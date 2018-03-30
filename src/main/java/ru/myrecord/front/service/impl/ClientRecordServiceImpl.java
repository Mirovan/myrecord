package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientRecordDAO;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.CalendarService;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.ScheduleService;
import ru.myrecord.front.service.iface.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Service("clientRecordService")
public class ClientRecordServiceImpl implements ClientRecordService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("clientRecordDAO")
    private ClientRecordDAO clientRecordDAO;

    @Autowired
    private UserService userService;

    @Autowired
    private CalendarService calendarService;

    @Autowired
    private ScheduleService scheduleService;

    @Override
    public ClientRecord add(ClientRecord clientRecord, User ownerUser) {
        User client = clientRecord.getUser();
        if (client.getEmail() == null) {
            String email = client.getPhone() + "@mail.ru";
            clientRecord.getUser().setEmail(email);
        }

        if (client.getOwnerUser() == null)
            client.setOwnerUser(ownerUser);

        if (client.getPass() == null)
            client.setPass( userService.generatePassword((new Random(10000000L)).toString()) );

        clientRecord.setActive(true);

        return clientRecordDAO.saveAndFlush(clientRecord);
    }


    @Override
    public ClientRecord findByUser(User user) {
        return null;
    }


    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User ownerUser) {
        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
        for (CalendarAdapter item : calendar) {
            //null - это просто пустые дни в начале месяца начиная с понедельника
            if (item != null) {
                //Находим всех мастеров, у кого есть в расписании этот день
                Set<User> users = userService.findWorkersByDate(item.getDate(), ownerUser);
                Set<UserAdapter> usersAdapter =
                        userService.getUserAdapterCollection(users);
                item.setData(usersAdapter);
            }
        }

        return calendar;
    }


    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, Product product, User ownerUser) {
        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
        for (CalendarAdapter item : calendar) {
            //null - это просто пустые дни в начале месяца начиная с понедельника
            if (item != null) {
                //Находим всех мастеров, у кого есть в расписании этот день
                Set<User> users = userService.findWorkersByDateAndProduct(item.getDate(), product, ownerUser);
                Set<UserAdapter> usersAdapter = userService.getUserAdapterCollection(users);
                item.setData(usersAdapter);
            }
        }

        return calendar;
    }


    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User worker, User ownerUser) {
        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
        for (CalendarAdapter item : calendar) {
            //null - это просто пустые дни в начале месяца начиная с понедельника
            if (item != null) {
                //Находим всех мастеров, у кого есть в расписании этот день
                Schedule schedule = scheduleService.findByUserAndDate(worker, item.getDate());
                if (schedule != null) {
                    UserAdapter userAdapter = userService.getUserAdapter(worker);
                    //добавляем всего одного сотрудника в сэт
                    Set<UserAdapter> users = new HashSet<>();
                    users.add(userAdapter);
                    item.setData(users);
                }
            }
        }

        return calendar;
    }


}
