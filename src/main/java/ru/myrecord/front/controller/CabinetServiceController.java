package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.Utils.Utils;
import ru.myrecord.front.data.model.Service;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.ServiceService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.ArrayList;

/**
 * Created by max on 02.12.2017.
 */

@Controller
public class CabinetServiceController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ServiceService serviceService;

    @RequestMapping(value="/cabinet/services/", method = RequestMethod.GET)
    public ModelAndView services(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("services", serviceService.findByActive(user));
        modelAndView.setViewName("cabinet/service/index");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/services/add/", method = RequestMethod.GET)
    public ModelAndView serviceAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");
        modelAndView.addObject("service", new Service());
        modelAndView.setViewName("cabinet/service/edit");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/services/add/", method = RequestMethod.POST)
    public ModelAndView serviceAddPost(Service service, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        service.setUser(user);
        service.setActive(true);
        serviceService.add(service);
        return new ModelAndView("redirect:/cabinet/services/");
    }

    @RequestMapping(value="/cabinet/services/edit/{serviceId}/", method = RequestMethod.GET)
    public ModelAndView serviceUpdate(@PathVariable Long serviceId) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "edit");
        Service service = serviceService.findServiceById(serviceId);
        if (service.getActive() == true) {
            modelAndView.addObject("service", service);
            modelAndView.setViewName("cabinet/service/edit");
        } else {
            return new ModelAndView("redirect:/cabinet/services/");
        }
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/services/edit/", method = RequestMethod.POST)
    public ModelAndView serviceEditPost(Service serviceUpd, Principal principal) {
        Service service = serviceService.findServiceById(serviceUpd.getId());
        User user = service.getUser();
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), user.getId()) ) {
            service.setName( serviceUpd.getName() );
            serviceService.update(service);
        }
        return new ModelAndView("redirect:/cabinet/services/");
    }

    @RequestMapping(value="/cabinet/services/delete/{serviceId}/", method = RequestMethod.GET)
    public ModelAndView servicePost(@PathVariable Long serviceId, Principal principal) {
        Service service = serviceService.findServiceById(serviceId);
        User user = service.getUser();
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), user.getId()) ) {
            service.setActive(false);
            serviceService.update(service);
        }
        return new ModelAndView("redirect:/cabinet/services/");
    }


}
