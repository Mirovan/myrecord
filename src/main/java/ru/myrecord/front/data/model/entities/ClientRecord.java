package ru.myrecord.front.data.model.entities;

import ru.myrecord.front.Utils.LocalDateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Запись клиента
 * */
@Entity
@Table(name = "client_record")
public class ClientRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "sdate", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = LocalDateConverter.class)
    private LocalDate sdate;

    @Column(name = "active")
    private Boolean active;

    //ToDo: сделать линковку на ClientRecordProduct

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ClientRecord(User user, LocalDate sdate) {
        this.user = user;
        this.sdate = sdate;
        this.active = true;
    }

    public ClientRecord() {
        super();
    }
}
