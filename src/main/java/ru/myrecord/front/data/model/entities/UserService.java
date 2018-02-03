package ru.myrecord.front.data.model.entities;

import javax.persistence.*;

@Entity
@Table(name = "user_service")
public class UserService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToMany(mappedBy = "service", cascade = CascadeType.ALL)
    @JoinColumn(name = "service_id")
    private Service service;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }
}
