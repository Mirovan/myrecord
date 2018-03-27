package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;

public interface ClientRecordService {
    ClientRecord add(ClientRecord clientRecord, User ownerUser);
    ClientRecord findByUser(User user);
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User ownerUser);
}
