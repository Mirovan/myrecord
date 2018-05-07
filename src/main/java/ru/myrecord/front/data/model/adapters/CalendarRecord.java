package ru.myrecord.front.data.model.adapters;

/**
 * Класс для отображения и передачи через JSON в JS-скрипт fullcalendar
 */

/*
{
 start: '2018-04-24',
 end: '2018-04-28',
 overlap: false,
 rendering: 'background',
 color: '#ff9f89'
 }
* */
public class CalendarRecord {
    private String id;
    private String title;
    private String start;
    private String end;
    private String allDay;
    private String resourceId;
    private boolean overlap;
    private String rendering;
    private String color;

    public CalendarRecord(String id, String title, String start, String end) {
        this.id = id;
        this.title = title;
        this.start = start;
        this.end = end;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public boolean isOverlap() {
        return overlap;
    }

    public void setOverlap(boolean overlap) {
        this.overlap = overlap;
    }

    public String getRendering() {
        return rendering;
    }

    public void setRendering(String rendering) {
        this.rendering = rendering;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
