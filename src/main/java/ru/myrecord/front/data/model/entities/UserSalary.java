package ru.myrecord.front.data.model.entities;

import ru.myrecord.front.Utils.LocalDateTimeConverter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_salary")
public class UserSalary {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "worker_id")
    private User worker;

    @Column(name = "salary")
    private Float salary;

    @Column(name = "salarypercent")
    private Float salaryPercent;

    @Column(name = "startdate", columnDefinition = "DATE")
    @NotNull
    @Convert(converter = LocalDateTimeConverter.class)
    private LocalDateTime startdate;

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
}
