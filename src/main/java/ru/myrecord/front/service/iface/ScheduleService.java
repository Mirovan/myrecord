package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ScheduleService {
    List<Schedule> findByWorker(User worker);
    Schedule findByWorkerAndDate(User worker, LocalDate date);
    void add(Schedule schedule);
    void removeScheduleByDate(User user, LocalDate date);
    List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User worker, User ownerUser);
    Set<Schedule> findWorkersByDate(LocalDate date, User ownerUser);    //поиск сотрудников по дате работы
    boolean hasWorkerWorkingDay(User worker, LocalDate date);   //Работает ли сотрудник в определенный день
}
