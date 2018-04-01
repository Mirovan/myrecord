package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.helpers.CalendarRecord;
import ru.myrecord.front.service.iface.ClientRecordProductService;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ClientRecordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private ClientRecordProductService clientRecordProductService;


    /**
     * Запрос месяца
     * */
    @RequestMapping(value="/cabinet/clients/json-calendar/", method = RequestMethod.GET)
    public List<CalendarAdapter> getCalendar(@RequestParam(required = false) Integer year,
                                             @RequestParam(required = false) Integer month,
                                             @RequestParam(required = false) Integer productId,
                                             @RequestParam(required = false) Integer userId,
                                             Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        List<CalendarAdapter> calendar = null;
        if (productId != null && userId != null) {
            User worker = userService.findUserById(userId);
            calendar = clientRecordService.getMonthCalendar(year, month, worker, ownerUser);
        } else if (productId != null) {
            Product product = productService.findProductById(productId);
            calendar = clientRecordService.getMonthCalendar(year, month, product, ownerUser);
        } else if (userId != null) {
            User worker = userService.findUserById(userId);
            calendar = clientRecordService.getMonthCalendar(year, month, worker, ownerUser);
        } else {
            calendar = clientRecordService.getMonthCalendar(year, month, ownerUser);
        }
        return calendar;
    }


    /**
     * Отображаем мастеров по выбранному продукту
     * */
    @RequestMapping(value="/cabinet/clients/json-users-by-product/", method = RequestMethod.GET)
    public Set<UserAdapter> getMastersByProduct(@RequestParam(required = false) Integer productId, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<User> users = null;
        if (productId != null) {
            Product product = productService.findProductById(productId);
            users = userService.findWorkersByProduct(product);
        } else {
            users = userService.findWorkersByOwner(ownerUser);
        }
        Set<UserAdapter> usersAdapter = userService.getUserAdapterCollection(users);
        return usersAdapter;
    }


    /**
     * Отображаем мастеров по выбранной дате
     * */
    @RequestMapping(value="/cabinet/clients/json-users-by-date/", method = RequestMethod.GET)
    public Set<CalendarRecord> getMasterRecordsByDate(Integer day,
                                                      Integer month,
                                                      Integer year,
                                                      Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<CalendarRecord> calendarMap = new HashSet<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.of(year, month, day);

        Set<ClientRecordProduct> clientRecords = clientRecordProductService.findByDate(date);

        for (ClientRecordProduct item : clientRecords) {
            LocalDateTime start = item.getSdate();
            LocalDateTime end = item.getSdate().plusHours(2);   //ToDo: сделать наминальное время оказания услуги

            String name = item.getRecord().getUser().getName() +  " " + item.getRecord().getUser().getSirname();
            UserAdapter master = userService.getUserAdapter(item.getMaster());

            calendarMap.add(new CalendarRecord(item.getId(), name, start.format(formatter), end.format(formatter), "", master));
        }

//        LocalDateTime start = LocalDateTime.of(year, month, day, 10, 0);
//        LocalDateTime end = LocalDateTime.of(year, month, day, 12, 0);
//        calendarMap.add(new CalendarRecord(0, "Elena", start.format(formatter), end.format(formatter), ""));
//
//        start = LocalDateTime.of(year, month, day, 11, 0);
//        end = LocalDateTime.of(year, month, day, 15, 0);

        return calendarMap;
    }

}
