package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarAdapter;

import java.util.List;

public interface CalendarService {
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month);
}
