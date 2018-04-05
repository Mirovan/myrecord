package ru.myrecord.front.data.model.adapters;

/**
 * Класс для отображения и передачи через JSON в JS-скрипт fullcalendar
 */
public class CalendarWorker {
    private int id;
    private String title;

    public CalendarWorker(int id, String title) {
        this.id = id;
        this.title = title;
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
}
