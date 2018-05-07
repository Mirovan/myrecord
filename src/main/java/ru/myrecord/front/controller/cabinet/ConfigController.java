package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.WorkerSalary;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
public class ConfigController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ConfigService configService;

    /**
     * Страница настроек
     * */
    @RequestMapping(value="/cabinet/config/", method = RequestMethod.GET)
    public ModelAndView showConfig(Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Config config = configService.findByOwnerUser(ownerUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("config", config);
        modelAndView.addObject("menuSelect", "config");
        modelAndView.setViewName("cabinet/config/index");
        return modelAndView;
    }

    /**
     * Сохранение настроек
     * */
    @RequestMapping(value="/cabinet/config/", method = RequestMethod.POST)
    public ModelAndView showConfigPost(Config updateConfig, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());

        updateConfig.setOwnerUser(ownerUser);
        configService.update(updateConfig);

        return new ModelAndView("redirect:/cabinet/config/");
    }
}
