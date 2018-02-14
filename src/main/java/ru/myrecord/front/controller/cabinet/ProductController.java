package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
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

    @RequestMapping(value="/cabinet/products/", method = RequestMethod.GET)
    public ModelAndView services(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("product", productService.findProductsByUser(user));
        modelAndView.setViewName("cabinet/product/index");
        return modelAndView;
    }


//    @RequestMapping(value="/cabinet/services/add/", method = RequestMethod.GET)
//    public ModelAndView serviceAdd() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("action", "add");
//        modelAndView.addObject("service", new Product());
//        modelAndView.setViewName("cabinet/service/edit");
//        return modelAndView;
//    }


    @RequestMapping(value="/cabinet/rooms/{roomId}/addproduct/", method = RequestMethod.GET)
    public ModelAndView serviceAdd(@PathVariable Integer roomId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            Room room = roomService.findRoomById(roomId);
            User user = room.getOwnerUser();
            Set<Room> rooms = roomService.findRoomsByActive(user);
            Product product = new Product();
            product.setRoom(room);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "add");
            modelAndView.addObject("rooms", rooms);
            modelAndView.addObject("product", product);
            modelAndView.setViewName("cabinet/product/edit");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/products/");
        }
    }


    @RequestMapping(value="/cabinet/products/add/", method = RequestMethod.POST)
    public ModelAndView serviceAddPost(Product product, Principal principal) {
        Room room = product.getRoom();
        User ownerUser = userService.findUserByEmail(principal.getName());
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, room.getId()) ) {
            product.setOwnerUser(ownerUser);
            product.setActive(true);
            productService.add(product);
            return new ModelAndView("redirect:/cabinet/products/");
        } else {
            return new ModelAndView("redirect:/cabinet/products/");
        }
    }


    @RequestMapping(value="/cabinet/products/edit/{productId}/", method = RequestMethod.GET)
    public ModelAndView serviceUpdate(@PathVariable Integer productId, Principal principal) {
        Product product = productService.findProductById(productId);
        User ownerUser = product.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasProduct(principal, productId) ) {
            Set<Room> rooms = roomService.findRoomsByActive(ownerUser);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "edit");
            modelAndView.addObject("rooms", rooms);
            modelAndView.addObject("product", product);
            modelAndView.setViewName("cabinet/product/edit");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/products/");
        }
    }


    @RequestMapping(value="/cabinet/products/edit/", method = RequestMethod.POST)
    public ModelAndView serviceEditPost(Product productUpd, Principal principal) {
        Product product = productService.findProductById(productUpd.getId());
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasProduct(principal, productUpd.getId()) ) {
            product.setName( productUpd.getName() );
            product.setRoom( productUpd.getRoom() );
            productService.update(product);
        }
        return new ModelAndView("redirect:/cabinet/products/");
    }


    @RequestMapping(value="/cabinet/products/delete/{productId}/", method = RequestMethod.GET)
    public ModelAndView servicePost(@PathVariable Integer productId, Principal principal) {
        Product product = productService.findProductById(productId);
        User ownerUser = product.getOwnerUser();
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasProduct(principal, productId) ) {
            product.setActive(false);
            productService.update(product);
        }
        return new ModelAndView("redirect:/cabinet/products/");
    }

}
