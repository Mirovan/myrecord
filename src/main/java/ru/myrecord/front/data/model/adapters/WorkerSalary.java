package ru.myrecord.front.data.model.adapters;

import ru.myrecord.front.data.model.entities.User;

public class WorkerSalary {
    private User worker;

    private Integer salaryByRenderProducts;  //сумма за оказанные услуги

    private Integer monthSalary;    //месячый оклад

    private Integer salaryByPercent; //процент за оказанные услуги

    private Integer salaryByPercentProducts; //сумма - процент за оказанные услуги по услугам

    public WorkerSalary(User worker, Integer salaryByRenderProducts, Integer monthSalary, Integer salaryByPercent, Integer salaryByPercentProducts) {
        this.worker = worker;
        this.salaryByRenderProducts = salaryByRenderProducts;
        this.monthSalary = monthSalary;
        this.salaryByPercent = salaryByPercent;
        this.salaryByPercentProducts = salaryByPercentProducts;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Integer getSalaryByRenderProducts() {
        return salaryByRenderProducts;
    }

    public void setSalaryByRenderProducts(Integer salaryByRenderProducts) {
        this.salaryByRenderProducts = salaryByRenderProducts;
    }

    public Integer getMonthSalary() {
        return monthSalary;
    }

    public void setMonthSalary(Integer monthSalary) {
        this.monthSalary = monthSalary;
    }

    public Integer getSalaryByPercent() {
        return salaryByPercent;
    }

    public void setSalaryByPercent(Integer salaryByPercent) {
        this.salaryByPercent = salaryByPercent;
    }

    public Integer getSalaryByPercentProducts() {
        return salaryByPercentProducts;
    }

    public void setSalaryByPercentProducts(Integer salaryByPercentProducts) {
        this.salaryByPercentProducts = salaryByPercentProducts;
    }
}
