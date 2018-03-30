package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserAdapter;
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
    private ProductService productService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private ClientRecordProductService clientRecordProductService;

    /**
     * Форма записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/record/", method = RequestMethod.GET)
    public ModelAndView showMonthCalendar(Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<User> users = userService.findWorkersByOwner(ownerUser);
        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);

        LocalDate date = LocalDate.now();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("year", date.getYear());
        modelAndView.addObject("month", date.getMonthValue());
        modelAndView.addObject("users", users);
        modelAndView.addObject("products", products);
        modelAndView.setViewName("/cabinet/client/record/index");
        return modelAndView;
    }


    /**
     * Календарь для определенного дня
     * */
    //@RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/product/{productId}/master/{masterId}/", method = RequestMethod.GET)
    @RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/", method = RequestMethod.GET)
    public ModelAndView showDailyCalendar(@PathVariable Integer day,
                                          @PathVariable Integer month,
                                          @PathVariable Integer year,
                                          //@RequestParam(required = false) Integer productId,
                                          //@RequestParam(required = false) Integer masterId,
                                          Principal principal) {
        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
        if ( true ) {
            User ownerUser = userService.findUserByEmail(principal.getName());

            //Находим всех мастеров кто работает в этот день
            LocalDate date = LocalDate.of(year, month, day);
            Set<User> workers = userService.findWorkersByDate(date, ownerUser);
            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("workers" , workersAdapter);
            modelAndView.setViewName("cabinet/client/record/dailycalendar");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Форма записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/record/add/", method = RequestMethod.GET)
    public ModelAndView addClientRecord(Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);

        Set<User> masters = userService.findWorkersByOwner(ownerUser);

        User client = new User();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("products", products);
        modelAndView.addObject("masters", masters);
        modelAndView.addObject("client", client);
        modelAndView.setViewName("cabinet/client/record/edit");
        return modelAndView;
    }


    /**
     * Сохранение записи клиента
     * */
    //ToDo: do
    @RequestMapping(value="/cabinet/clients/record/add/", method = RequestMethod.POST)
    public ModelAndView editClientRecordPost(User client,
                                             Integer productId,
                                             Integer masterId,
                                             String sdate,
                                             Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( true ) {
            //ToDo: принадлежит сист.пользователю продуктЫ, клиент

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime recordDate = LocalDateTime.parse(sdate, formatter);

            User ownerUser = userService.findUserByEmail(principal.getName());
            ClientRecord clientRecord = new ClientRecord(client);
            clientRecord = clientRecordService.add(clientRecord, ownerUser);

            ClientRecordProduct clientRecordProduct = new ClientRecordProduct();
            clientRecordProduct.setRecord(clientRecord);
            Product product = productService.findProductById(productId);
            clientRecordProduct.setProduct(product);
            User master = userService.findUserById(masterId);
            clientRecordProduct.setMaster(master);
            clientRecordProduct.setSdate(recordDate);
            clientRecordProductService.add(clientRecordProduct);

            return new ModelAndView("redirect:/cabinet/clients/record/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


}
