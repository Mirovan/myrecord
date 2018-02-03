package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ScheduleDAO;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ScheduleService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("scheduleService")
public class ScheduleServiceImpl implements ScheduleService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("scheduleDAO")
    private ScheduleDAO scheduleDAO;

    @Override
    public List<Schedule> findByUser(User user) {
        return scheduleDAO.findByUser(user);
    }

    @Override
    public void add(Schedule schedule) {
        scheduleDAO.save(schedule);
    }

    @Override
    public Schedule findByUserAndSdate(User user, LocalDate sdate) {
        return scheduleDAO.findByUserAndSdate(user, sdate);
    }

    @Override
    public void removeScheduleByDate(User user, LocalDate date) {
        scheduleDAO.deleteByUserAndSdate(user, date);
    }

    /**
     * Получаем список - месячный календарь
     * */
    @Override
    public List<List<Schedule>> getMonthSchedule(Integer year, Integer month) {
        List<List<Schedule>> scheduleAll = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);   //Дата по году и месяцу

        //Заполняем нулями первые элементы массива, в зависимости каким был первый день месяца
        scheduleAll.add(new ArrayList<>());
        for (int i=1; i<date.withDayOfMonth(1).getDayOfWeek().getValue(); i++) {
            scheduleAll.get(scheduleAll.size()-1).add( new Schedule() );
        }
        //Заполняем двуменрый массив датами
        for (int i=1; i<=date.lengthOfMonth(); i++) {
            //увеличиваем размер массива
            if ( date.withDayOfMonth(i).getDayOfWeek().getValue() == 1 ) {
                scheduleAll.add(new ArrayList<>());
            }
            //создаем День
            Schedule schedule = new Schedule();
            schedule.setSdate(date.withDayOfMonth(i));
            scheduleAll.get(scheduleAll.size()-1).add( schedule );
        }
        return scheduleAll;
    }

}
