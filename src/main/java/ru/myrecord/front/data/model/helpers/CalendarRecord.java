package ru.myrecord.front.data.model.helpers;

/**
 * Класс для отображения и передачи через JSON в JS-скрипт fullcalendar
 */
public class CalendarRecord {
    private int id;
    private String title;
    private String start;
    private String end;
    private String allDay;

    public CalendarRecord(int id, String title, String start, String end, String allDay) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
        this.allDay = allDay;
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
}
