package ru.myrecord.front.data.model.entities.organisation;

import ru.myrecord.front.Utils.LocalDateConverter;
import ru.myrecord.front.data.model.entities.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "organisationBalance")
public class OrganisationBalance {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    //Владелец - системный пользователь
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_user_id")
    private User user;

    @Column(name = "expDate", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = LocalDateConverter.class)
    private LocalDate expDate;

    @Column(name = "balance")
    private Float balance;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "orgTarif")
    private OrgTarif orgTarif;


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

    public LocalDate getExpDate() {
        return expDate;
    }

    public void setExpDate(LocalDate expDate) {
        this.expDate = expDate;
    }

    public Float getBalance() {
        return balance;
    }

    public void setBalance(Float balance) {
        this.balance = balance;
    }

    public OrgTarif getOrgTarif() {
        return orgTarif;
    }

    public void setOrgTarif(OrgTarif orgTarif) {
        this.orgTarif = orgTarif;
    }
}
