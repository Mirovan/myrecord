package ru.myrecord.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class IndexController/* implements ErrorController*/{

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("redirect:/cabinet/");
    }

    @RequestMapping(value={"/cabinet/", "/cabinet"}, method = RequestMethod.GET)
    public ModelAndView cabinet() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("menuSelect", "config");
        modelAndView.setViewName("cabinet/index");
        return modelAndView;
    }

//    @RequestMapping(value="/temp/", method = RequestMethod.GET)
//    public ModelAndView temp() {
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.setViewName("temp");
//        return modelAndView;
//    }

}
