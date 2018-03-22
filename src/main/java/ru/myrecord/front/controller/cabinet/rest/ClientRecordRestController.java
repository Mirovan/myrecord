package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.adapters.ScheduleAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.util.Set;

/**
 * Created by RuAV on 07.12.2017.
 * Этот класс потом надо разделить на несколько,
 * по логике
 */
@RestController
public class ClientRecordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRecordService clientRecordService;


    /**
     * Запрос месяца
     * */
    @RequestMapping(value="/cabinet/clients/record/json-month-clendar/", method = RequestMethod.GET)
    public ScheduleAdapter getScheduleData(Integer userId, Integer year, Integer month, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();

        ScheduleAdapter userMonthSchedule = new ScheduleAdapter();


        //    userMonthSchedule.setScheduleAll( scheduleService.getMonthSchedule(year, month) );  //Получаем список - месячный календарь
        //    userMonthSchedule.setUserSchedule( scheduleService.findByUser(user) );  //Получаем расписание пользователя
        return userMonthSchedule;
    }

}
