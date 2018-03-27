package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.adapters.CalendarRecordDayAdapter;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.ScheduleService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class ClientRecordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRecordService clientRecordService;

    /**
     * Запрос месяца
     * */
    @RequestMapping(value="/cabinet/clients/calendar/", method = RequestMethod.GET)
    public List<List<CalendarRecordDayAdapter>> getCalendar(Integer year, Integer month, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        List<List<CalendarRecordDayAdapter>> calendar = clientRecordService.getMonthCalendar(year, month, ownerUser);
        return calendar;
    }

}
