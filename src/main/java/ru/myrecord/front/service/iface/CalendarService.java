package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;

public interface CalendarService {
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month);
}
