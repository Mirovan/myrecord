package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.service.iface.UserService;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class CabinetController/* implements ErrorController*/{

    @Autowired
    @Qualifier("userService")
    UserService UserService;

    @RequestMapping(value="/cabinet/", method = RequestMethod.GET)
    public ModelAndView cabinet(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/index");
        return modelAndView;
    }


    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView users(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/users");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/services/", method = RequestMethod.GET)
    public ModelAndView services(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/services");
        return modelAndView;
    }
}
