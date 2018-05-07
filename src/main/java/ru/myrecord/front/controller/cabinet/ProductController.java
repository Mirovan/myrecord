package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.RoomProductsAdapter;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserProductService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by max on 02.12.2017.
 */

@Controller
public class ProductController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserProductService userProductService;

    /**
     * Помещения и Все продукты в них
     * */
    @RequestMapping(value="/cabinet/products/", method = RequestMethod.GET)
    public ModelAndView showRoomsProducts(Principal principal) {
        User ownerUser = userService.findUserByEmail( principal.getName() );
        //Ищем все комнаты пользователя
        List<Room> rooms = roomService.findRoomsByActive(ownerUser);

        List<RoomProductsAdapter> roomProducts = new ArrayList<>();
        //Ищем все услуги в этих комнатах
        for (Room room: rooms) {
            Set<Product> products = productService.findProductsByRoom(room);
            roomProducts.add(new RoomProductsAdapter(room, products));
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("roomProducts", roomProducts);
        modelAndView.setViewName("cabinet/product/index");
        return modelAndView;
    }


    /**
     * Мастера которые оказывают услугу
     * */
    @RequestMapping(value="/cabinet/products/{productId}/users/", method = RequestMethod.GET)
    public ModelAndView showProductUsers(@PathVariable Integer productId, Principal principal) {
        if ( userService.hasProduct(principal, productId) ) {
            User ownerUser = userService.findUserByEmail( principal.getName() );
            Product product = productService.findProductById(productId);
            Room room = roomService.findRoomById(product.getRoom().getId());
            //Ищем всех пользователей
            Set<User> users = userService.findWorkersByOwner(ownerUser);
            Set<User> involveUsers = new HashSet<>();   //Оказывают эту услугу
            Set<User> freeUsers = new HashSet<>();      //Не оказывают эту услугу
            //Определяем оказывает ли пользователь эту услугу
            for (User user: users) {
                if ( userProductService.hasUserProductActiveLink(user, product) )
                    involveUsers.add(user);
                else
                    freeUsers.add(user);
            }

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("product", product);
            modelAndView.addObject("room", room);
            modelAndView.addObject("involveUsers", involveUsers);
            modelAndView.addObject("freeUsers", freeUsers);
            modelAndView.setViewName("cabinet/product/users/index");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Сохраняем мастеров кто оказывает эту услугу
     * */
    @RequestMapping(value="/cabinet/products/addusers/", method = RequestMethod.POST)
    public ModelAndView addProductUsers(@RequestParam Integer productId,
                                        @RequestParam(value="involveUsers[]", required = false) List<Integer> involveUsers,
                                        @RequestParam(value="freeUsers[]", required = false) List<Integer> freeUsers,
                                        Principal principal) {
        if ( userService.hasProduct(principal, productId) ) {
            Product product = productService.findProductById(productId);
            User ownerUser = userService.findUserByEmail( principal.getName() );
            //Ищем всех пользователей
            Set<User> users = userService.findWorkersByOwner(ownerUser);
            //находим всех пользователей у owner'a

            //Id-шники юзеров которые оказывают услуги пришедшие через POST
            Set<Integer> userIds = new HashSet<>();
            if (involveUsers != null) userIds.addAll(involveUsers);
            if (freeUsers != null) userIds.addAll(freeUsers);

            for (User user: users) {
                //Если у юзера была эта услуга и сейчас её нет в POST (т.е. услугу убрали)
                //то деактивируем её
                if ( userProductService.hasUserProductActiveLink(user, product) && !userIds.contains(user.getId()) ) {
                    UserProduct userProduct = userProductService.findByUserAndProductActiveLink(user, product);
                    userProduct.setActive(false);  //отключаем эту услугу
                    userProductService.update(userProduct);
                }
                //Если была юзер-продукт связь была и она не активна
                else if ( userProductService.hasUserProductAnyLink(user, product) && userIds.contains(user.getId()) ) {
                    UserProduct userProduct = userProductService.findByUserAndProductAnyLink(user, product);
                    userProduct.setActive(true);  //включаем эту услугу
                    userProductService.update(userProduct);
                }
                //Если услуги не было у пользователя и её сейчас добавили
                //то создаем её
                else if ( !userProductService.hasUserProductAnyLink(user, product) && userIds.contains(user.getId()) ) {
                    UserProduct userProduct = new UserProduct(user, product);
                    userProduct.setActive(true);
                    userProductService.add(userProduct);
                }
            }

            return new ModelAndView("redirect:/cabinet/products/" + String.valueOf(productId) + "/users/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }

    /**
     * Добавление услуг в опреденное помещение
     * */
    @RequestMapping(value="/cabinet/rooms/{roomId}/addproduct/", method = RequestMethod.GET)
    public ModelAndView addProductToRoom(@PathVariable Integer roomId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            Room room = roomService.findRoomById(roomId);
            User user = room.getOwnerUser();
            List<Room> rooms = roomService.findRoomsByActive(user);
            Product product = new Product();
            product.setRoom(room);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "add");
            modelAndView.addObject("rooms", rooms);
            modelAndView.addObject("product", product);
            modelAndView.setViewName("cabinet/room/product/editproduct");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }

    /**
     * Добавление услуги без привязки к комнате
     * */
    @RequestMapping(value="/cabinet/rooms/addproduct/", method = RequestMethod.GET)
    public ModelAndView addProductToRoom(Principal principal) {
        User ownerUser = userService.findUserByEmail( principal.getName() );
        List<Room> rooms = roomService.findRoomsByActive(ownerUser);
        Product product = new Product();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");
        modelAndView.addObject("rooms", rooms);
        modelAndView.addObject("product", product);
        modelAndView.setViewName("cabinet/room/product/editproduct");
        return modelAndView;
    }


    @RequestMapping(value="/cabinet/products/add/", method = RequestMethod.POST)
    public ModelAndView addProductToRoomPost(Product product, Principal principal) {
        Room room = product.getRoom();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, room.getId()) ) {
            product.setActive(true);
            productService.add(product);
            return new ModelAndView("redirect:/cabinet/rooms/" + room.getId() + "/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    @RequestMapping(value="/cabinet/products/edit/{productId}/", method = RequestMethod.GET)
    public ModelAndView updateProduct(@PathVariable Integer productId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasProduct(principal, productId) ) {
            Product product = productService.findProductById(productId);
            User ownerUser = product.getRoom().getOwnerUser();   //пользователь которому принадлежит помещение с этой услугой
            List<Room> rooms = roomService.findRoomsByActive(ownerUser);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "edit");
            modelAndView.addObject("rooms", rooms);
            modelAndView.addObject("product", product);
            modelAndView.setViewName("cabinet/room/product/editproduct");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    @RequestMapping(value="/cabinet/products/edit/", method = RequestMethod.POST)
    public ModelAndView productEditPost(Product productUpd, Principal principal) {
        Product product = productService.findProductById(productUpd.getId());
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasProduct(principal, productUpd.getId()) ) {
            product.setName( productUpd.getName() );
            product.setRoom( productUpd.getRoom() );
            productService.update(product);
        }
        return new ModelAndView("redirect:/cabinet/");
    }


    @RequestMapping(value="/cabinet/products/delete/{productId}/", method = RequestMethod.GET)
    public ModelAndView productPost(@PathVariable Integer productId, Principal principal) {
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasProduct(principal, productId) ) {
            Product product = productService.findProductById(productId);
            product.setActive(false);
            productService.update(product);
        }
        return new ModelAndView("redirect:/cabinet/");
    }

}
