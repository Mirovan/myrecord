package ru.myrecord.front.data.model.entities;

import ru.myrecord.front.Utils.LocalDateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_product_salary")
public class UserProductSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private User worker;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id")
    private Product product;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "salarypercent")
    private Float salaryPercent;

    @Column(name = "startdate", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startdate;

    @Column(name = "enddate", columnDefinition = "DATE")
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime enddate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Float getSalary() {
        return salary;
    }

    public void setSalary(Float salary) {
        this.salary = salary;
    }

    public Float getSalaryPercent() {
        return salaryPercent;
    }

    public void setSalaryPercent(Float salaryPercent) {
        this.salaryPercent = salaryPercent;
    }

    public LocalDateTime getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDateTime startdate) {
        this.startdate = startdate;
    }

    public LocalDateTime getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDateTime enddate) {
        this.enddate = enddate;
    }

    public UserProductSalary() {
        super();
    }

    public UserProductSalary(User worker, Product product, Float salary, Float salaryPercent) {
        this.worker = worker;
        this.product = product;
        this.salary = salary;
        this.salaryPercent = salaryPercent;
    }
}
