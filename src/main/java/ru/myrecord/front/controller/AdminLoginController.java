package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
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
public class AdminLoginController/* implements ErrorController*/{

    @Autowired
    @Qualifier("sysUserService")
    SysUserService sysUserService;

    /*
    * Страница логина в админке
    * */
    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
    public ModelAndView indexGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/index");
        return ( mav );
    }

    /*
    * Отправили логин-пароль, пришел POST-запрос
    * */
    @RequestMapping(value = "/admin/", method = RequestMethod.POST)
    public ModelAndView indexPost() {
        //Пытаемся авторизовать пользователя

        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/index");
        return ( mav );
    }

}
