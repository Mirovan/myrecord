package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarRecordDayAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;

public interface ClientRecordService {
    ClientRecord add(ClientRecord clientRecord, User ownerUser);
    ClientRecord findByUser(User user);
    List<List<CalendarRecordDayAdapter>> getMonthCalendar(Integer year, Integer month, User ownerUser);
}
