package ru.myrecord.front.data.model.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "client_payment")
public class ClientPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @OneToOne
    @JoinColumn(name = "client_record_id", nullable = false)
    private ClientRecord clientRecord;

    @Column(name = "ispaid")
    private Boolean isPaid;

    @Column(name = "active")
    private Boolean active;

    @OneToMany(mappedBy = "clientPayment", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<ClientPaymentProduct> clientPaymentProducts;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientRecord getClientRecord() {
        return clientRecord;
    }

    public void setClientRecord(ClientRecord clientRecord) {
        this.clientRecord = clientRecord;
    }

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public List<ClientPaymentProduct> getClientPaymentProducts() {
        return clientPaymentProducts;
    }

    public void setClientPaymentProducts(List<ClientPaymentProduct> clientPaymentProducts) {
        this.clientPaymentProducts = clientPaymentProducts;
    }

    public ClientPayment(ClientRecord clientRecord, Boolean isPaid, Boolean active) {
        this.clientRecord = clientRecord;
        this.isPaid = isPaid;
        this.active = active;
    }

    public ClientPayment() {
        super();
    }
}
