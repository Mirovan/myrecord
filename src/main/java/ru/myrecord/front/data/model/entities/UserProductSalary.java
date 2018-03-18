package ru.myrecord.front.data.model.entities;

import ru.myrecord.front.Utils.LocalDateConverter;
import ru.myrecord.front.Utils.LocalDateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "user_product_salary")
public class UserProductSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

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
    private LocalDate startdate;

    @Column(name = "enddate", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDate enddate;

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

    public LocalDate getStartdate() {
        return startdate;
    }

    public void setStartdate(LocalDate startdate) {
        this.startdate = startdate;
    }

    public LocalDate getEnddate() {
        return enddate;
    }

    public void setEnddate(LocalDate enddate) {
        this.enddate = enddate;
    }
}
