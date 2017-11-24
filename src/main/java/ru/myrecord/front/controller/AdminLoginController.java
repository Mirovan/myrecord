package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import ru.myrecord.front.service.UserService;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class AdminLoginController implements ErrorController {

    @Autowired
    @Qualifier("userService")
    UserService UserService;

    /*
    * Страница логина в админке
    * */
//    @RequestMapping(value = "/admin/", method = RequestMethod.GET)
//    public ModelAndView indexGet() {
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("admin/index");
//        return ( mav );
//    }

    /*
    * Отправили логин-пароль, пришел POST-запрос
    * */
//    @RequestMapping(value = "/admin/", method = RequestMethod.POST)
//    public ModelAndView indexPost() {
//        //Пытаемся авторизовать пользователя
//
//        ModelAndView mav = new ModelAndView();
//        mav.setViewName("admin/index");
//        return ( mav );
//    }

    @Override
    public String getErrorPath() {
        return null;
    }
}
