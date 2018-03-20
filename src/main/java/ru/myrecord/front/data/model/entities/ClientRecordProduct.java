package ru.myrecord.front.data.model.entities;

import ru.myrecord.front.Utils.LocalDateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_salary")
public class ClientRecordProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "record_id")
    private ClientRecord record;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "sdate", columnDefinition = "DATE")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime sdate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public ClientRecord getRecord() {
        return record;
    }

    public void setRecord(ClientRecord record) {
        this.record = record;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public LocalDateTime getSdate() {
        return sdate;
    }

    public void setSdate(LocalDateTime sdate) {
        this.sdate = sdate;
    }
}
