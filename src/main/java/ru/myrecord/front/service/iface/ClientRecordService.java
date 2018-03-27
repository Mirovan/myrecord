package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserSalary;

import java.time.LocalDate;
import java.util.List;

public interface ClientRecordService {
    ClientRecord add(ClientRecord clientRecord, User ownerUser);
    ClientRecord findByUser(User user);
    List<List<LocalDate>> getMonthCalendar(Integer year, Integer month);
}
