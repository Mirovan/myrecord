package ru.myrecord.front.data.model;

import javax.persistence.*;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 * Created by max on 16.12.2017.
 */

@Entity
@Table(name = "user_schedule")
public class Schedule {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    User user;

    @Column(name = "sdate")
    LocalDate sdate;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getSdate() {
        return sdate;
    }

    public void setSdate(LocalDate sdate) {
        this.sdate = sdate;
    }
}
