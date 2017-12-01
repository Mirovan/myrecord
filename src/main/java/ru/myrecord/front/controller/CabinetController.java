package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.dao.RoomDAO;
import ru.myrecord.front.data.dao.UserDAO;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import ru.myrecord.front.Utils.Utils;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class CabinetController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;



    @RequestMapping(value="/cabinet/", method = RequestMethod.GET)
    public ModelAndView cabinet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/index");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/users");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/rooms/", method = RequestMethod.GET)
    public ModelAndView rooms(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "rooms", roomService.findByActive(user) );
        modelAndView.setViewName("cabinet/room/index");
        return modelAndView;
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
        User user = userService.findUserByEmail(principal.getName());
        room.setUser(user);
        room.setActive(true);
        roomService.add(room);
        return new ModelAndView("redirect:/cabinet/rooms/");
    }

    @RequestMapping(value="/cabinet/rooms/edit/{roomId}/", method = RequestMethod.GET)
    public ModelAndView roomUpdate(@PathVariable Long roomId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "edit");
        Room room = roomService.findRoomById(roomId);
        if (room.getActive() == true) {
            modelAndView.addObject("room", room);
            modelAndView.setViewName("cabinet/room/edit");
        } else {
            return new ModelAndView("redirect:/cabinet/rooms/");
        }
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/rooms/edit/", method = RequestMethod.POST)
    public ModelAndView roomEditPost(Room roomUpd, Principal principal) {
        Room room = roomService.findRoomById(roomUpd.getId());
        User user = room.getUser();
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), user.getId()) ) {
            room.setName( roomUpd.getName() );
            roomService.update(room);
        }
        return new ModelAndView("redirect:/cabinet/rooms/");
    }

    @RequestMapping(value="/cabinet/rooms/delete/{roomId}/", method = RequestMethod.GET)
    public ModelAndView roomPost(@PathVariable Long roomId, Principal principal) {
        Room room = roomService.findRoomById(roomId);
        User user = room.getUser();
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), user.getId()) ) {
            room.setActive(false);
            roomService.update(room);
        }
        return new ModelAndView("redirect:/cabinet/rooms/");
    }


//    @RequestMapping(value="/cabinet/services/", method = RequestMethod.GET)
//    public ModelAndView services() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("cabinet/service/index");
//        return modelAndView;
//    }


}
