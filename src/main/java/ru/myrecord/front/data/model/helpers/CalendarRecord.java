package ru.myrecord.front.data.model.helpers;

import ru.myrecord.front.data.model.adapters.UserAdapter;

/**
 * Класс для отображения и передачи через JSON в JS-скрипт fullcalendar
 */
public class CalendarRecord {
    private int id;
    private String title;
    private String start;
    private String end;
    private String allDay;
    private UserAdapter master;

    public CalendarRecord(int id, String title, String start, String end, String allDay, UserAdapter master) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
        this.master = master;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getAllDay() {
        return allDay;
    }

    public void setAllDay(String allDay) {
        this.allDay = allDay;
    }

    public UserAdapter getMaster() {
        return master;
    }

    public void setMaster(UserAdapter master) {
        this.master = master;
    }
}
