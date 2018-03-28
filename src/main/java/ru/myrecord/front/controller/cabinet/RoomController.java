package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserProductService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * Created by max on 12.11.2017.
 */

@Controller
public class RoomController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;


    @RequestMapping(value="/cabinet/rooms/", method = RequestMethod.GET)
    public ModelAndView index(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        Set<Room> temp = roomService.findRoomsByActive(user);
        modelAndView.addObject( "rooms", roomService.findRoomsByActive(user) );
        modelAndView.setViewName("cabinet/room/index");
        return modelAndView;
    }


    @RequestMapping(value="/cabinet/rooms/{roomId}/", method = RequestMethod.GET)
    public ModelAndView roomsShow(@PathVariable Integer roomId, Principal principal) {
        Room room = roomService.findRoomById(roomId);
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            //Услуги в помещении
            Set<Product> products = productService.findProductsByRoom(room);

            //Пользователи в помещении
            Set<User> users = userService.findUsersByRoom(room);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("products", products);
            modelAndView.addObject("users", users);
            modelAndView.setViewName("cabinet/room/product");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/rooms/");
        }
    }


    @RequestMapping(value="/cabinet/rooms/add/", method = RequestMethod.GET)
    public ModelAndView roomAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");
        modelAndView.addObject("room", new Room());
        modelAndView.setViewName("cabinet/room/edit");
        return modelAndView;
    }


    @RequestMapping(value="/cabinet/rooms/add/", method = RequestMethod.POST)
    public ModelAndView roomAddPost(Room room, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        room.setOwnerUser(ownerUser);
        room.setActive(true);
        roomService.add(room);
        return new ModelAndView("redirect:/cabinet/rooms/");
    }


    @RequestMapping(value="/cabinet/rooms/edit/{roomId}/", method = RequestMethod.GET)
    public ModelAndView roomUpdate(@PathVariable Integer roomId, Principal principal) {
        Room room = roomService.findRoomById(roomId);
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "edit");
            if (room.getActive() == true) {
                modelAndView.addObject("room", room);
                modelAndView.setViewName("cabinet/room/edit");
            } else {
                return new ModelAndView("redirect:/cabinet/rooms/");
            }
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/rooms/");
        }
    }


    @RequestMapping(value="/cabinet/rooms/edit/", method = RequestMethod.POST)
    public ModelAndView roomEditPost(Room roomUpd, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomUpd.getId()) ) {
            Room room = roomService.findRoomById(roomUpd.getId());
            room.setName( roomUpd.getName() );
            roomService.update(room);
        }
        return new ModelAndView("redirect:/cabinet/rooms/");
    }


    @RequestMapping(value="/cabinet/rooms/delete/{roomId}/", method = RequestMethod.GET)
    public ModelAndView roomPost(@PathVariable Integer roomId, Principal principal) {
        Room room = roomService.findRoomById(roomId);
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            room.setActive(false);
            roomService.update(room);
        }
        return new ModelAndView("redirect:/cabinet/rooms/");
    }


    /**
     * Форма добавления пользователя в помещение
     * */
    @RequestMapping(value="/cabinet/rooms/{roomId}/addusers/", method = RequestMethod.GET)
    public ModelAndView addUsersToRoom(@PathVariable Integer roomId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            Room room = roomService.findRoomById(roomId);
            User ownerUser = room.getOwnerUser();
            Set<Product> products = productService.findProductsByRoom(room);
            //Отображаем только нужные данные о пользователях используя Адаптер
            Set<UserAdapter> users = userService.getUserAdapterCollection( userService.findWorkersByOwner(ownerUser) );

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("roomId", roomId);
            modelAndView.addObject("users", users);
            modelAndView.addObject("products", products);
            modelAndView.setViewName("cabinet/room/adduser");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Добавление пользователя в помещение и его услуг
     * */
    @RequestMapping(value="/cabinet/rooms/users/add/", method = RequestMethod.POST)
    public ModelAndView addUsersToRoomPost(@RequestParam Integer roomId,
                                           @RequestParam Integer userId,
                                           @RequestParam(value="products[]", required = false) List<Integer> productsIds,
                                           Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) && userService.hasRoom(principal, roomId) && userService.hasProducts(principal, productsIds) ) {
            Room room = roomService.findRoomById(roomId);
            User user = userService.findUserById(userId);
            Set<Product> products = new HashSet<>();
            for (Integer item: productsIds) {
                products.add(productService.findProductById(item));
            }

            //Линкуем пользователя к услугам
            for (Product product: products) {
                //Если нет такой услуги, то создаем линк
                if ( !userProductService.hasUserProductAnyLink(user, product) ) {
                    UserProduct userProduct = new UserProduct(user, product);
                    userProduct.setActive(true);
                    userProductService.add(userProduct);
                }
                //Если линк у юзера к продукту есть, но он не активен, то активируем
                else if ( userProductService.hasUserProductActiveLink(user, product) ) {
                    UserProduct userProduct = userProductService.findByUserAndProductAnyLink(user, product);
                    userProduct.setActive(true);
                    userProductService.update(userProduct);
                } else {
                    UserProduct userProduct = userProductService.findByUserAndProductActiveLink(user, product);
                }
            }

            return new ModelAndView("redirect:/cabinet/rooms/" + String.valueOf(roomId) + "/users/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Форма отображения пользователей в помещении
     * */
    @RequestMapping(value="/cabinet/rooms/{roomId}/users/", method = RequestMethod.GET)
    public ModelAndView viewRoomUsers(@PathVariable Integer roomId, Principal principal) {
        //ToDo: отображаем пользователей в этой комнате
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            Room room = roomService.findRoomById(roomId);
            Set<User> users = userService.findUsersByRoom(room);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("users", users);
            modelAndView.setViewName("cabinet/room/users/index");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }
}
