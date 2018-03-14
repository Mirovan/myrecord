package ru.myrecord.front.data.model.entities;

import javax.persistence.*;

/*
 * Из-за поля active линкуем таблицы через дополнительную сущность
 * */
@Entity
@Table(name = "user_room")
public class UserRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;


    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column(name = "active")
    private Boolean active;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public UserRoom() {
        super();
    }

    public UserRoom(User user, Room room) {
        this.user = user;
        this.room = room;
    }
}
