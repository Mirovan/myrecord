package ru.myrecord.front.data.model.restadapters;

import ru.myrecord.front.data.model.Schedule;

import java.util.List;

/**
 * Created by sbt-milovanov-mm on 02.02.2018.
 *
 * Класс для отображения данных через REST
 */
public class ScheduleAdapter {
    private List<Schedule> userSchedule;
    private List<List<Schedule>> scheduleAll;

    public List<Schedule> getUserSchedule() {
        return userSchedule;
    }

    public void setUserSchedule(List<Schedule> userSchedule) {
        this.userSchedule = userSchedule;
    }

    public List<List<Schedule>> getScheduleAll() {
        return scheduleAll;
    }

    public void setScheduleAll(List<List<Schedule>> scheduleAll) {
        this.scheduleAll = scheduleAll;
    }
}