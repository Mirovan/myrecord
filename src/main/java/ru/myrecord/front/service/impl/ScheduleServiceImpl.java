package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ScheduleDAO;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.adapters.ScheduleAdapter;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.CalendarService;
import ru.myrecord.front.service.iface.ScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("scheduleDAO")
    private ScheduleDAO scheduleDAO;

    @Autowired
    private CalendarService calendarService;

    @Override
    public List<Schedule> findByUser(User user) {
        return scheduleDAO.findByUser(user);
    }

    @Override
    public void add(Schedule schedule) {
        scheduleDAO.save(schedule);
    }

    @Override
    public Schedule findByUserAndDate(User user, LocalDate date) {
        return scheduleDAO.findByUserAndSdate(user, date);
    }

    @Override
    public void removeScheduleByDate(User user, LocalDate date) {
        scheduleDAO.deleteByUserAndSdate(user, date);
    }

    /**
     * Получаем список - месячный календарь
     * */
    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User ownerUser, User user) {
        //Расписание пользователя
        List<Schedule> userSchedule = findByUser(user);

        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
        for (CalendarAdapter item : calendar) {
            //null - это просто пустые дни в начале месяца начиная с понедельника
            if (item != null) {
                Schedule schedule = findByUserAndDate(user, item.getDate());
                //ToDo: сделать через адаптер
                //ScheduleAdapter scheduleAdapter = new ScheduleAdapter();
                item.setData(schedule);
            }
        }

        return calendar;
    }


    @Override
    public Set<Schedule> findByDate(LocalDate date, User ownerUser) {
        //Todo: улучшить выборку - добавить еще чтобы выбирал по ownerUser
        return scheduleDAO.findBySdate(date);
    }

}
