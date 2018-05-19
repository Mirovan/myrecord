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
import ru.myrecord.front.service.iface.*;

import java.time.LocalDate;
import java.util.*;

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

    @Autowired
    private ClientRecordProductService clientRecordProductService;

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
    public ClientRecord findById(Integer id) {
        return clientRecordDAO.findById(id).get();
    }

    @Override
    public List<ClientRecord> findByDate(LocalDate date, User ownerUser) {
        Set<User> clients = userService.findClientsByOwner(ownerUser);
        return clientRecordDAO.findByDateAndUserInAndActiveTrue(date, clients);
    }


    /**
     * Отображение месячного календаря для записи
     * */
    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, Product product, User worker, User ownerUser) {
        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
        for (CalendarAdapter item : calendar) {
            //null - это просто пустые дни в начале месяца начиная с понедельника
            if (item != null) {
                //Если не указан сотрудник и услуга
                if (product == null && worker == null) {
                    item.setData( getWorkersByDate(item.getDate(), ownerUser) );
                } else if (worker != null) {
                    if (scheduleService.hasWorkerWorkingDay(worker, item.getDate())) {
                        item.setData(
                                userService.getUserAdapterCollection( Collections.singleton(worker) )
                        );
                    }
                } else if (product != null) {
                    item.setData( getWorkersByDateAndProduct(item.getDate(), product, ownerUser) );
                }
            }
        }

        return calendar;
    }

//
//    /**
//     * Получаем сотрудника, кто работает в этот день
//     * */
//    private Object getWorkerByDate(LocalDate date, User worker, User ownerUser) {
//        //находим всех сотрудников кто работает в этот день
//        Set<UserAdapter> workers = getWorkersByDate(date, ownerUser);
//
//        //если сотрудник работает в этот день
//        if (workers != null && workers.contains(userService.getUserAdapter(worker)))
//            return userService.getUserAdapterCollection( Collections.singleton(worker) );
//        else
//            return null;
//    }


    /**
     * Находим всех мастеров, у кого есть в расписании этот день
     * */
    private Set<UserAdapter> getWorkersByDate(LocalDate date, User ownerUser) {
        Set<User> users = userService.findWorkersByDate(date, ownerUser);
        return userService.getUserAdapterCollection(users);
    }


    /**
     * Находим всех мастеров, у кого есть в расписании этот день, которые оказывают услугу product
     * */
    private Object getWorkersByDateAndProduct(LocalDate date, Product product, User ownerUser) {
        Set<User> users = userService.findWorkersByDateAndProduct(date, product, ownerUser);
        return userService.getUserAdapterCollection(users);
    }



//    /**
//     * Отображение месячного календаря для записи
//     * */
//    @Override
//    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, Product product, User ownerUser) {
//        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
//        for (CalendarAdapter item : calendar) {
//            //null - это просто пустые дни в начале месяца начиная с понедельника
//            if (item != null) {
//                //Находим всех мастеров, у кого есть в расписании этот день
//                Set<User> users = userService.findWorkersByDateAndProduct(item.getDate(), product, ownerUser);
//                Set<UserAdapter> usersAdapter = userService.getUserAdapterCollection(users);
//                item.setData(usersAdapter);
//            }
//        }
//
//        return calendar;
//    }
//
//
//    /**
//     * Отображение месячного календаря для записи
//     * */
//    @Override
//    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User worker, User ownerUser) {
//        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
//        for (CalendarAdapter item : calendar) {
//            //null - это просто пустые дни в начале месяца начиная с понедельника
//            if (item != null) {
//                //Находим всех мастеров, у кого есть в расписании этот день
//                Schedule schedule = scheduleService.findByWorkerAndDate(worker, item.getDate());
//                if (schedule != null) {
//                    UserAdapter userAdapter = userService.getUserAdapter(worker);
//                    //добавляем всего одного сотрудника в сэт
//                    Set<UserAdapter> users = new HashSet<>();
//                    users.add(userAdapter);
//                    item.setData(users);
//                }
//            }
//        }
//
//        return calendar;
//    }


    @Override
    public List<ClientRecord> findByDates(LocalDate from, LocalDate to, User ownerUser) {
        //ToDo: добавить список клиентов, чтобы найти среди них у кого ownerUser
        //Находим всех клиентов у ownerUser
        Set<User> clients = userService.findClientsByOwner(ownerUser);
        return clientRecordDAO.findByDateBetweenAndUserInAndActiveTrue(from, to, clients);
    }

}
