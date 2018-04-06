package ru.myrecord.front.data.model.adapters;

import ru.myrecord.front.data.model.entities.ClientPaymentProduct;
import ru.myrecord.front.data.model.entities.User;

public class WorkerSalary {
    private User worker;

    private Integer salary;

    public WorkerSalary(User worker, Integer salary) {
        this.worker = worker;
        this.salary = salary;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Integer getSalary() {
        return salary;
    }

    public void setSalary(Integer salary) {
        this.salary = salary;
    }
}
