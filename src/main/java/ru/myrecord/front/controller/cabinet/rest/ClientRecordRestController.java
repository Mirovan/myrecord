package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
public class ClientRecordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRecordService clientRecordService;

    /**
     * Запрос месяца
     * */
    @RequestMapping(value="/cabinet/clients/calendar/", method = RequestMethod.GET)
    public List<CalendarAdapter> getCalendar(@RequestParam(required = false) Integer year,
                                             @RequestParam(required = false) Integer month,
                                             @RequestParam(required = false) Integer productId,
                                             @RequestParam(required = false) Integer userId,
                                             Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        List<CalendarAdapter> calendar = null;
        if (productId == null && userId == null) {
            calendar = clientRecordService.getMonthCalendar(year, month, ownerUser);
        } else if (productId != null) {
            Product product = productService.findProductById(productId);
            calendar = clientRecordService.getMonthCalendar(year, month, product, ownerUser);
        } else if (userId != null) {
            User worker = userService.findUserById(userId);
            calendar = clientRecordService.getMonthCalendar(year, month, worker, ownerUser);
        } else {

        }
        return calendar;
    }

}
