package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ScheduleService {
    List<Schedule> findByUser(User user);
    Schedule findByUserAndDate(User user, LocalDate date);
    void add(Schedule schedule);
    void removeScheduleByDate(User user, LocalDate date);
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User ownerUser, User user);
    Set<Schedule> findByDate(LocalDate date);
}
