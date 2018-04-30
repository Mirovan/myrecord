package ru.myrecord.front.data.model.entities;


import ru.myrecord.front.Utils.LocalDateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //Владелец - системный пользователь
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id")
    private User user;

    @Column(name = "price")
    private float price;

    @Column(name = "paymentDate", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = LocalDateConverter.class)
    private LocalDate paymentDate;

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

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
}
