package ru.myrecord.front.data.model.entities;

import javax.persistence.*;

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

    @Column(name = "active")
    private Boolean active;

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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public ClientRecord() {
        super();
    }

    public ClientRecord(User user) {
        this.user = user;
    }
}
