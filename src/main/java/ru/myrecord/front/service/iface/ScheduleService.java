package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;
import java.util.List;

public interface ScheduleService {
    List<Schedule> findByUser(User user);
    Schedule findByUserAndSdate(User user, LocalDate sdate);
    void add(Schedule schedule);
    void removeScheduleByDate(User user, LocalDate date);
    List<List<Schedule>> getMonthSchedule(Integer year, Integer month);
}
