package ru.myrecord.front.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.service.iface.UserService;

/**
 * Created by max on 12.11.2017.
 */

@Controller
@RequestMapping("/admin")
public class AdminLoginController implements ErrorController {

    @Autowired
    @Qualifier("userService")
    UserService UserService;

    /*
    * Страница логина в админке
    * */
    @RequestMapping(value = {"/", "/index"}, method = RequestMethod.GET)
    public ModelAndView indexGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/index");
        return ( mav );
    }

    /*
    * Страница диаграм в админке
    * */
    @RequestMapping(value = "/charts", method = RequestMethod.GET)
    public ModelAndView chartsGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/charts");
        return ( mav );
    }

    /*
    * Страница форм в админке
    * */
    @RequestMapping(value = "/forms", method = RequestMethod.GET)
    public ModelAndView formsGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/forms");
        return ( mav );
    }

    /*
    * Страница таблиц в админке
    * */
    @RequestMapping(value = "/tables", method = RequestMethod.GET)
    public ModelAndView tablesGet() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("admin/tables");
        return ( mav );
    }


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
