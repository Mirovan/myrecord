package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
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
    private UserProductService userProductService;

    @Autowired
    private ClientRecordProductService clientRecordProductService;

    /**
     * Календарь работы всех мастеров
     * */
    @RequestMapping(value="/cabinet/clients/record/", method = RequestMethod.GET)
    public ModelAndView showMonthCalendar(Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<User> workers = userService.findWorkersByOwner(ownerUser);
        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);

        LocalDate date = LocalDate.now();

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("year", date.getYear());
        modelAndView.addObject("month", date.getMonthValue());
        modelAndView.addObject("workers", workers);
        modelAndView.addObject("products", products);
        modelAndView.setViewName("/cabinet/client/record/index");
        return modelAndView;
    }


    /**
     * Календарь для определенного дня
     * */
    @RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/", method = RequestMethod.GET)
    public ModelAndView showDailyCalendar(@PathVariable Integer day,
                                          @PathVariable Integer month,
                                          @PathVariable Integer year,
                                          Principal principal) {
        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
        if ( true ) {
            User ownerUser = userService.findUserByEmail(principal.getName());

            //Находим всех мастеров кто работает в этот день
            LocalDate date = LocalDate.of(year, month, day);
            Set<User> workers = userService.findWorkersByDate(date, ownerUser);
            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);
            Set<Product> products = new HashSet<>();
            for (User worker : workers) {
                Set<Product> workerProducts = productService.findProductsByWorker(worker);
                products.addAll(workerProducts);
            }

            User client = new User();

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("workers" , workersAdapter);
            modelAndView.addObject("day", day);
            modelAndView.addObject("month", month);
            modelAndView.addObject("year", year);
            modelAndView.addObject("client", client);
            modelAndView.addObject("products", products);
            modelAndView.setViewName("cabinet/client/record/dailycalendar");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Календарь для определенного дня и услуги
     * */
    @RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/product/{productId}/", method = RequestMethod.GET)
    public ModelAndView showDailyCalendarByProduct(@PathVariable Integer day,
                                                   @PathVariable Integer month,
                                                   @PathVariable Integer year,
                                                   @PathVariable Integer productId,
                                                   Principal principal) {
        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
        if ( true ) {
            User ownerUser = userService.findUserByEmail(principal.getName());

            //Находим всех мастеров кто работает в этот день
            LocalDate date = LocalDate.of(year, month, day);
            Product product = productService.findProductById(productId);
            Set<Product> products = new HashSet<>();
            products.add(product);
            Set<User> workers = userService.findWorkersByDateAndProduct(date, product, ownerUser);
            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);

            User client = new User();

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("day", day);
            modelAndView.addObject("month", month);
            modelAndView.addObject("year", year);
            modelAndView.addObject("client", client);
            modelAndView.addObject("workers" , workersAdapter);
            modelAndView.addObject("products" , products);
            modelAndView.setViewName("cabinet/client/record/dailycalendar");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Календарь для определенного дня и пользователя
     * */
    @RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/worker/{workerId}/", method = RequestMethod.GET)
    public ModelAndView showDailyCalendarByUser(@PathVariable Integer day,
                                                @PathVariable Integer month,
                                                @PathVariable Integer year,
                                                @PathVariable Integer workerId,
                                                Principal principal) {
        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
        if ( true ) {
            User ownerUser = userService.findUserByEmail(principal.getName());

            LocalDate date = LocalDate.of(year, month, day);
            User worker = userService.findUserById(workerId);
            Set<User> workers = new HashSet<>();
            workers.add(worker);
            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);

            Set<Product> products = productService.findProductsByWorker(worker);

            User client = new User();

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("day", day);
            modelAndView.addObject("month", month);
            modelAndView.addObject("year", year);
            modelAndView.addObject("client", client);
            modelAndView.addObject("workers" , workersAdapter);
            modelAndView.addObject("products" , products);
            modelAndView.setViewName("cabinet/client/record/dailycalendar");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Простая Форма записи клиента
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

            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            LocalDateTime recordDateTime = LocalDateTime.parse(sdate, timeFormatter);
            LocalDate recordDate = LocalDate.parse(sdate, timeFormatter);

            User ownerUser = userService.findUserByEmail(principal.getName());
            ClientRecord clientRecord = new ClientRecord(client, recordDate);
            clientRecord = clientRecordService.add(clientRecord, ownerUser);

            ClientRecordProduct clientRecordProduct = new ClientRecordProduct();
            clientRecordProduct.setClientRecord(clientRecord);
            Product product = productService.findProductById(productId);
            clientRecordProduct.setProduct(product);
            User master = userService.findUserById(masterId);
            clientRecordProduct.setMaster(master);
            clientRecordProduct.setSdate(recordDateTime);
            clientRecordProductService.add(clientRecordProduct);

            return new ModelAndView("redirect:/cabinet/clients/record/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


}
