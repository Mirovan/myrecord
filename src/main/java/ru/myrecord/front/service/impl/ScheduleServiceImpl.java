package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ScheduleDAO;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.CalendarService;
import ru.myrecord.front.service.iface.ScheduleService;
import ru.myrecord.front.service.iface.UserService;

import java.time.LocalDate;
import java.util.HashSet;
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

    @Autowired
    private UserService userService;

    @Override
    public List<Schedule> findByWorker(User worker) {
        return scheduleDAO.findByWorker(worker);
    }

    @Override
    public void add(Schedule schedule) {
        scheduleDAO.save(schedule);
    }

    @Override
    public Schedule findByWorkerAndDate(User worker, LocalDate date) {
        return scheduleDAO.findByWorkerAndSdate(worker, date);
    }

    @Override
    public void removeScheduleByDate(User worker, LocalDate date) {
        scheduleDAO.deleteByWorkerAndSdate(worker, date);
    }


    /**
     * Получаем список - месячный календарь
     * */
    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month, User worker, User ownerUser) {
        //Расписание пользователя
        List<Schedule> userSchedule = findByWorker(worker);

        List<CalendarAdapter> calendar = calendarService.getMonthCalendar(year, month);
        for (CalendarAdapter item : calendar) {
            //null - это просто пустые дни в начале месяца начиная с понедельника
            if (item != null) {
                Schedule schedule = findByWorkerAndDate(worker, item.getDate());
                //ToDo: сделать через адаптер
                //ScheduleAdapter scheduleAdapter = new ScheduleAdapter();
                item.setData(schedule);
            }
        }

        return calendar;
    }


    /**
     * поиск сотрудников по дате работы
     * */
    @Override
    public Set<Schedule> findWorkersByDate(LocalDate date, User ownerUser) {
        //Todo: улучшить выборку
        Set<Schedule> allWorkersSchedule = scheduleDAO.findBySdate(date);
        Set<Schedule> result = new HashSet<>();
        for (Schedule item: allWorkersSchedule) {
            if ( userService.hasUser(ownerUser, item.getWorker().getId()) )
                result.add( item );
        }
        return result;
    }


    /**
     * Работает ли сотрудник в определенный день
     * */
    @Override
    public boolean hasWorkerWorkingDay(User worker, LocalDate date) {
        Schedule schedule = findByWorkerAndDate(worker, date);
        if (schedule == null)
            return false;
        else
            return true;
    }

}
