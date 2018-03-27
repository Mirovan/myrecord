package ru.myrecord.front.data.model.adapters;

import java.time.LocalDate;
import java.util.Set;

/**
 * Класс для отображения данных через REST
 */
public class CalendarAdapter<T> {
    private LocalDate date; //дата
    private T data;

    public CalendarAdapter() {
        super();
    }

    public CalendarAdapter(LocalDate date, T data) {
        this.date = date;
        this.data = data;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}