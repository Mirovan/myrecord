package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ClientRecordService {
    ClientRecord add(ClientRecord clientRecord, User ownerUser);
    ClientRecord findByUser(User user);
    ClientRecord findById(Integer Id);   //Поиск по Id
    Set<ClientRecord> findByDate(LocalDate date);   //Поиск по дате
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User ownerUser);
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, Product product, User ownerUser);
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User worker, User ownerUser);
    List<ClientRecord> findByDates(LocalDate from, LocalDate to, User ownerUser);
}
