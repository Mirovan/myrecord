package ru.myrecord.front.data.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "config")
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //Владелец - системный пользователь
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id")
    private User ownerUser;

    @Column(name = "is_set_schedule")
    private Boolean isSetSchedule;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public void setOwnerUser(User ownerUser) {
        this.ownerUser = ownerUser;
    }

    public Boolean getSetSchedule() {
        return isSetSchedule;
    }

    public void setSetSchedule(Boolean setSchedule) {
        isSetSchedule = setSchedule;
    }
}
