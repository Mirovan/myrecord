package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserProductAdapter;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordProductService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class IndexController/* implements ErrorController*/{

    @Autowired
    private ClientRecordProductService clientRecordProductService;

    @Autowired
    private UserService userService;


    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("redirect:/cabinet/");
    }


    @RequestMapping(value={"/cabinet/", "/cabinet"}, method = RequestMethod.GET)
    public ModelAndView cabinet(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();

        User ownerUser = userService.findUserByEmail(principal.getName());

        //Клиенты сегодня
        List<UserProductAdapter> todayClients = clientRecordProductService.getClientsByDate(ownerUser, LocalDate.now());

        //Клиенты кто пришел к нам n-дней назад
        List<UserProductAdapter> remindClients = clientRecordProductService.getRemindClientsByDate(ownerUser, LocalDate.now());

        modelAndView.addObject("todayClients", todayClients);
        modelAndView.addObject("remindClients", remindClients);
        modelAndView.addObject("menuSelect", "home");
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
