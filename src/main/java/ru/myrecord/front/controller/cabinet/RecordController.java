package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;


@Controller
public class    RecordController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private ClientRecordProductService clientRecordProductService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

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
        modelAndView.addObject("menuSelect", "record");
        modelAndView.setViewName("cabinet/client/record/index");
        return modelAndView;
    }


    /**
     * Календарь для определенного дня
     * */
    //ToDo: переделать с учетом конфига без утановки расписания сотрудников
    @RequestMapping(value="/cabinet/clients/record-day/", method = RequestMethod.GET)
    public ModelAndView showDailyCalendar(
            @RequestParam(required = true) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate date,
            Principal principal) {
        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
        if ( true ) {
            User ownerUser = userService.findUserByEmail(principal.getName());
            Config config = configService.findByOwnerUser(ownerUser);

            String year = String.valueOf(date.getYear());
            String month = String.valueOf(date.getMonthValue());
            if (month.length() < 2) month = "0" + month;
            String day = String.valueOf(date.getDayOfMonth());
            if (day.length() < 2) day = "0" + day;

            Set<Product> products = null;
            Set<User> workers = null;

            //без учета расписания сотрудников
            if (config.getIsSetSchedule() == false) {
                //Находим всех мастеров без учета расписания
                workers = userService.findWorkersByOwner(ownerUser);
            } else {
                //Находим всех мастеров кто работает в этот день
                workers = userService.findWorkersByDate(date, ownerUser);
            }

            //Список сотрудников
            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);

            //Список услуг
            products = new HashSet<>();
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
            modelAndView.addObject("date", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
            modelAndView.addObject("client", client);
            modelAndView.addObject("products", products);
            modelAndView.addObject("menuSelect", "record");
            modelAndView.setViewName("cabinet/client/record/dailycalendar");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Календарь для определенного дня и услуги
     * */
//    @RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/product/{productId}/", method = RequestMethod.GET)
//    public ModelAndView showDailyCalendarByProduct(@PathVariable Integer day,
//                                                   @PathVariable Integer month,
//                                                   @PathVariable Integer year,
//                                                   @PathVariable Integer productId,
//                                                   Principal principal) {
//        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
//        if ( true ) {
//            User ownerUser = userService.findUserByEmail(principal.getName());
//
//            //Находим всех мастеров кто работает в этот день
//            LocalDate date = LocalDate.of(year, month, day);
//            Product product = productService.findProductById(productId);
//            Set<Product> products = new HashSet<>();
//            products.add(product);
//            Set<User> workers = userService.findWorkersByDateAndProduct(date, product, ownerUser);
//            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);
//
//            User client = new User();
//
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.addObject("day", day);
//            modelAndView.addObject("month", month);
//            modelAndView.addObject("year", year);
//            modelAndView.addObject("date", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//            modelAndView.addObject("client", client);
//            modelAndView.addObject("workers" , workersAdapter);
//            modelAndView.addObject("products" , products);
//            modelAndView.addObject("menuSelect", "record");
//            modelAndView.setViewName("cabinet/client/record/dailycalendar");
//            return modelAndView;
//        } else {
//            return new ModelAndView("redirect:/cabinet/");
//        }
//    }


    /**
     * Календарь для определенного дня и пользователя
     * */
//    @RequestMapping(value="/cabinet/clients/record-day/{day}/{month}/{year}/worker/{workerId}/", method = RequestMethod.GET)
//    public ModelAndView showDailyCalendarByUser(@PathVariable Integer day,
//                                                @PathVariable Integer month,
//                                                @PathVariable Integer year,
//                                                @PathVariable Integer workerId,
//                                                Principal principal) {
//        //ToDo: Проверка - имеет ли текущий пользователь записывать клиентов
//        if ( true ) {
//            User ownerUser = userService.findUserByEmail(principal.getName());
//
//            LocalDate date = LocalDate.of(year, month, day);
//            User worker = userService.findUserById(workerId);
//            Set<User> workers = new HashSet<>();
//            workers.add(worker);
//            Set<UserAdapter> workersAdapter = userService.getUserAdapterCollection(workers);
//
//            Set<Product> products = productService.findProductsByWorker(worker);
//
//            User client = new User();
//
//            ModelAndView modelAndView = new ModelAndView();
//            modelAndView.addObject("day", day);
//            modelAndView.addObject("month", month);
//            modelAndView.addObject("year", year);
//            modelAndView.addObject("date", date.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
//            modelAndView.addObject("client", client);
//            modelAndView.addObject("workers" , workersAdapter);
//            modelAndView.addObject("products" , products);
//            modelAndView.addObject("menuSelect", "record");
//            modelAndView.setViewName("cabinet/client/record/dailycalendar");
//            return modelAndView;
//        } else {
//            return new ModelAndView("redirect:/cabinet/");
//        }
//    }


    /**
     * Простая Форма записи клиента
     * */
//    @RequestMapping(value="/cabinet/clients/record/add/", method = RequestMethod.GET)
//    public ModelAndView addClientRecord(Principal principal) {
//        User ownerUser = userService.findUserByEmail(principal.getName());
//        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);
//
//        Set<User> masters = userService.findWorkersByOwner(ownerUser);
//
//        User client = new User();
//
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("products", products);
//        modelAndView.addObject("masters", masters);
//        modelAndView.addObject("client", client);
//        modelAndView.addObject("menuSelect", "record");
//        modelAndView.setViewName("cabinet/client/record/edit");
//        return modelAndView;
//    }


    /**
     * Сохранение записи клиента - новый клиент
     * */
    //ToDo: do
    @RequestMapping(value="/cabinet/clients/record/add/new/", method = RequestMethod.POST)
    public ModelAndView addClientRecordPost(User client,
                                            Integer productId,
                                            Integer workerId,
                                            String date,
                                            String time,
                                            Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());

        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //ToDo: принадлежит сист.пользователю продуктЫ, клиент
        if ( true ) {
            User worker = userService.findUserById(workerId);
            Product product = productService.findProductById(productId);

            //create new user
            client.setActive(true);
            Set<Role> roles = new HashSet<>();
            roles.add(roleService.findRoleByName("CLIENT"));
            client.setRoles(roles);
            client.setOwnerUser(ownerUser);
            String email = client.getPhone()
                    .replace("+", "")
                    .replace("-", "")
                    .replace("(", "")
                    .replace(")", "") + "@crm.bigint.ru";
            client.setEmail(email);
            String password = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
            client.setPass( bCryptPasswordEncoder.encode(password) );
            userService.addSimpleUser(client);

            doRecord(ownerUser, client, worker, product, date, time);

            return new ModelAndView("redirect:/cabinet/clients/record/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Сохранение записи клиента - существующий клиент
     * */
    //ToDo: do
    @RequestMapping(value="/cabinet/clients/record/add/exist/", method = RequestMethod.POST)
    public ModelAndView addClientRecordPost(Integer clientId,
                                            Integer productId,
                                            Integer workerId,
                                            String date,
                                            String time,
                                            Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());

        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //ToDo: принадлежит сист.пользователю продуктЫ, клиент
        if ( userService.hasUser(ownerUser, clientId) ) {
            User client = userService.findUserById(clientId);
            User worker = userService.findUserById(workerId);
            Product product = productService.findProductById(productId);

            doRecord(ownerUser, client, worker, product, date, time);

            return new ModelAndView("redirect:/cabinet/clients/record/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Создание DateTime из строковых параметров
     * */
    private LocalDateTime concatDateTime(String date, String time) {
        String dateTimeStr = date + " " + time;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(dateTimeStr, timeFormatter);
        return dateTime;
    }


    public void doRecord(User ownerUser, User client, User worker, Product product, String date, String time) {
        String datetimeStr = date + " " + time;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        LocalDateTime recordDateTime = LocalDateTime.parse(datetimeStr, timeFormatter);
        LocalDate recordDate = LocalDate.parse(datetimeStr, timeFormatter);

        ClientRecord clientRecord = new ClientRecord(client, recordDate);
        clientRecord = clientRecordService.add(clientRecord, ownerUser);

        ClientRecordProduct clientRecordProduct = new ClientRecordProduct();
        clientRecordProduct.setClientRecord(clientRecord);
        clientRecordProduct.setProduct(product);

        clientRecordProduct.setWorker(worker);
        clientRecordProduct.setSdate(recordDateTime);
        clientRecordProductService.add(clientRecordProduct);
    }

}
