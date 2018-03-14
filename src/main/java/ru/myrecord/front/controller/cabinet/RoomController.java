package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

}
