package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.UserService;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class IndexController/* implements ErrorController*/{

    @Autowired
    @Qualifier("userService")
    UserService UserService;

//    @RequestMapping("/")
//    public String welcome(Map<String, Object> model) {
//        model.put("var", "ddddddddddddddddddd");
//        return "index";
//    }

//
//    @RequestMapping(value = "/users/", method = RequestMethod.GET)
//    public ModelAndView users() {
//        ModelAndView mav = new ModelAndView();
//        //List<User> list = UserService.getAll();
//        //mav.addObject("Users", list);
//        mav.setViewName("users");
//        return ( mav );
//    }


//    @RequestMapping(value = "/login/", method = RequestMethod.GET)
//    public ModelAndView login() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("login");
//        return ( mav );
//    }



//    @RequestMapping(value = "/error")
//    public String error() {
//        return "Error handling";
//    }
//
//    @Override
//    public String getErrorPath() {
//        return "/error";
//    }

}
