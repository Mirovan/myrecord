package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientRecordDAO;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    public List<List<LocalDate>> getMonthCalendar(Integer year, Integer month) {
        List<List<LocalDate>> calendarAll = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);   //Дата по году и месяцу

        //Заполняем нулями первые элементы массива, в зависимости каким был первый день месяца
        calendarAll.add(new ArrayList<>());
        for (int i=1; i<date.withDayOfMonth(1).getDayOfWeek().getValue(); i++) {
            calendarAll.get(calendarAll.size()-1).add( null );
        }
        //Заполняем двуменрый массив датами
        for (int i=1; i<=date.lengthOfMonth(); i++) {
            //увеличиваем размер массива
            if ( date.withDayOfMonth(i).getDayOfWeek().getValue() == 1 ) {
                calendarAll.add(new ArrayList<>());
            }
            //создаем День
            LocalDate localDate = date.withDayOfMonth(i);
            calendarAll.get(calendarAll.size()-1).add( localDate );
        }
        return calendarAll;
    }


}
