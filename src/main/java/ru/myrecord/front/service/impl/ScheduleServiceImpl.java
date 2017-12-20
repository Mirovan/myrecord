package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.RoomDAO;
import ru.myrecord.front.data.dao.ScheduleDAO;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.Schedule;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.ScheduleService;

import java.util.Date;
import java.util.List;
import java.util.Set;

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
}
