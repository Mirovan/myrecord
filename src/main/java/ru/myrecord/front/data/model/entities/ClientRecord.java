package ru.myrecord.front.data.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ru.myrecord.front.Utils.LocalDateConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

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
    private LocalDate date;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "clientRecord", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ClientRecordProduct> clientRecordProducts;

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

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ClientRecordProduct> getClientRecordProducts() {
        return clientRecordProducts;
    }

    public void setClientRecordProducts(List<ClientRecordProduct> clientRecordProducts) {
        this.clientRecordProducts = clientRecordProducts;
    }

    public ClientRecord(User user, LocalDate date) {
        this.user = user;
        this.date = date;
        this.active = true;
    }

    public ClientRecord() {
        super();
    }
}
