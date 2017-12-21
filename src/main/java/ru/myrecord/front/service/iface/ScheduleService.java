package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.Schedule;
import ru.myrecord.front.data.model.User;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ScheduleService {
    List<Schedule> findByUser(User user);
    Schedule findByUserAndSdate(User user, LocalDate sdate);
    void add(Schedule schedule);
}
