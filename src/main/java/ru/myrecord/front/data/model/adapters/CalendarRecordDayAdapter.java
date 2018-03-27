package ru.myrecord.front.data.model.adapters;

import java.time.LocalDate;
import java.util.Set;

/**
 * Created by sbt-milovanov-mm on 02.02.2018.
 *
 * Класс для отображения данных через REST
 */
public class CalendarRecordDayAdapter {
    private LocalDate date; //дата
    private Set<UserAdapter> users;

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Set<UserAdapter> getUsers() {
        return users;
    }

    public void setUsers(Set<UserAdapter> users) {
        this.users = users;
    }

    public CalendarRecordDayAdapter(LocalDate date, Set<UserAdapter> users) {
        this.date = date;
        this.users = users;
    }
}