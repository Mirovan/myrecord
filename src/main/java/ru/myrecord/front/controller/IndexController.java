package ru.myrecord.front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class IndexController {

    @GetMapping("/")
    public ModelAndView index() {
        ModelAndView mav = new ModelAndView("index", "var", "Hello");
        return ( mav );
    }

}
