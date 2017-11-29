package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.dao.RoomDAO;
import ru.myrecord.front.data.dao.UserDAO;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;

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
    public ModelAndView rooms(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "rooms", roomService.findByActive() );
        modelAndView.setViewName("cabinet/room/index");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/rooms/add/", method = RequestMethod.GET)
    public ModelAndView roomAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("room", new Room());
        modelAndView.setViewName("cabinet/room/edit");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/rooms/add/", method = RequestMethod.POST)
    public ModelAndView roomAddPost(Room room, Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail( principal.getName() );
        room.setUser(user);
        room.setActive(true);
        roomService.add(room);
        modelAndView.setViewName("cabinet/room/edit");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/services/", method = RequestMethod.GET)
    public ModelAndView services() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/service/index");
        return modelAndView;
    }


}
