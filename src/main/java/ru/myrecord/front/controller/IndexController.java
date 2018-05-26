package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserProductAdapter;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.ClientRecordProductService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by max on 12.11.2017.
 */

@Controller
public class IndexController/* implements ErrorController*/{

    @Autowired
    ClientRecordProductService clientRecordProductService;

    @RequestMapping(value="/", method = RequestMethod.GET)
    public ModelAndView index(){
        return new ModelAndView("redirect:/cabinet/");
    }

    @RequestMapping(value={"/cabinet/", "/cabinet"}, method = RequestMethod.GET)
    public ModelAndView cabinet() {
        ModelAndView modelAndView = new ModelAndView();

        //Клиенты сегодня
        List<UserProductAdapter> todayClients = new ArrayList<>();

        Set<ClientRecordProduct> clientRecords = clientRecordProductService.findByDate(LocalDate.now());
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (ClientRecordProduct item : clientRecords) {
            UserProductAdapter userProduct = new UserProductAdapter(
                    item.getClientRecord().getUser(),
                    item.getProduct(),
                    item.getSdate().format(timeFormatter)
            );
            todayClients.add( userProduct );
        }

        modelAndView.addObject("todayClients", todayClients);
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
