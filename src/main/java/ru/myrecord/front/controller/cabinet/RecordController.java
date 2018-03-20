package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class RecordController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private UserProductSalaryService userProductSalaryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Форма записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/record/", method = RequestMethod.GET)
    public ModelAndView showClientRecords(Principal principal) {
        return new ModelAndView("/cabinet/client/record/index");
    }


    /**
     * Форма записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/{userId}/product/{productId}", method = RequestMethod.GET)
    public ModelAndView editClientRecord(@PathVariable Integer userId,
                                         @PathVariable Integer productId,
                                         Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) && userService.hasProduct(principal, productId) ) {

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("cabinet/client/record/edit");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Сохранение записи клиента
     * */
    //ToDo: do
    @RequestMapping(value="/cabinet/clients/record/", method = RequestMethod.POST)
    public ModelAndView editClientRecordPost(@RequestParam Integer userId,
                                             ClientRecord clientRecord,
                                             ClientRecordProduct clientRecordProduct,
                                             Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId)
            //ToDo: принадлежит сист.пользователю продуктЫ
                ) {
            User user = userService.findUserById(userId);

            return new ModelAndView("redirect:/cabinet/clients/record");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


}
