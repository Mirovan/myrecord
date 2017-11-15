package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.SysUser;
import ru.myrecord.front.service.SysUserService;

import java.util.List;
import java.util.Map;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class IndexController/* implements ErrorController*/{

    @Autowired
    @Qualifier("sysUserService")
    SysUserService sysUserService;

    @RequestMapping("/")
    public String welcome(Map<String, Object> model) {
        model.put("var", "ddddddddddddddddddd");
        return "index";
    }


    @RequestMapping(value = "/users/", method = RequestMethod.GET)
    public ModelAndView users() {
        ModelAndView mav = new ModelAndView();
        List<SysUser> list = sysUserService.getAll();
        mav.addObject("sysusers", list);
        mav.setViewName("users");
        return ( mav );
    }


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
