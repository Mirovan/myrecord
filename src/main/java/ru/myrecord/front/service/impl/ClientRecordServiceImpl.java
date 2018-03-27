package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientRecordDAO;
import ru.myrecord.front.data.model.adapters.CalendarRecordDayAdapter;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
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
    public List<List<CalendarRecordDayAdapter>> getMonthCalendar(Integer year, Integer month, User ownerUser) {
        List<List<CalendarRecordDayAdapter>> calendar = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);   //Дата по году и месяцу

        //Заполняем нулями первые элементы массива, в зависимости каким был первый день месяца
        calendar.add(new ArrayList<>());
        for (int i=1; i<date.withDayOfMonth(1).getDayOfWeek().getValue(); i++) {
            calendar.get(calendar.size()-1).add( null );
        }

        //Заполняем двуменрый массив датами
        for (int i=1; i<=date.lengthOfMonth(); i++) {
            //увеличиваем размер массива
            if ( date.withDayOfMonth(i).getDayOfWeek().getValue() == 1 ) {
                calendar.add(new ArrayList<>());
            }

            //создаем День
            LocalDate localDate = date.withDayOfMonth(i);
            //Находим всех мастеров, у кого есть в расписании этот день
            Set<UserAdapter> users = userService.getUserAdapterCollection( userService.findMastersByScheduleDay(localDate, ownerUser) );
            CalendarRecordDayAdapter calendarRecordDayAdapter = new CalendarRecordDayAdapter(localDate, users);
            calendar.get(calendar.size()-1).add(calendarRecordDayAdapter);
        }
        return calendar;
    }

}
