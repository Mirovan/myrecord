package ru.myrecord.front.data.model.adapters;

import ru.myrecord.front.data.model.entities.User;

public class MoneyStatistic {
    private User worker;

    private Integer earnedMoney;        //Выручка/Доход - сумма за оказанные услуги

    private Integer primeCostSum;          //сумма себестоимостей услуг

    private Integer workerPeriodSalary;        //оклад за период

    private Integer workerInterestSalary;    //З/п сотрудника - Процент от общей прибыли

    private Integer workerInterestProductSalary; //З/п сотрудника - Процент от прибыли по услугам

    public MoneyStatistic(User worker, Integer earnedMoney, Integer primeCostSum, Integer workerPeriodSalary, Integer workerInterestSalary, Integer workerInterestProductSalary) {
        this.worker = worker;
        this.earnedMoney = earnedMoney;
        this.primeCostSum = primeCostSum;
        this.workerPeriodSalary = workerPeriodSalary;
        this.workerInterestSalary = workerInterestSalary;
        this.workerInterestProductSalary = workerInterestProductSalary;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public Integer getEarnedMoney() {
        return earnedMoney;
    }

    public void setEarnedMoney(Integer earnedMoney) {
        this.earnedMoney = earnedMoney;
    }

    public Integer getPrimeCostSum() {
        return primeCostSum;
    }

    public void setPrimeCostSum(Integer primeCostSum) {
        this.primeCostSum = primeCostSum;
    }

    public Integer getWorkerPeriodSalary() {
        return workerPeriodSalary;
    }

    public void setWorkerPeriodSalary(Integer workerPeriodSalary) {
        this.workerPeriodSalary = workerPeriodSalary;
    }

    public Integer getWorkerInterestSalary() {
        return workerInterestSalary;
    }

    public void setWorkerInterestSalary(Integer workerInterestSalary) {
        this.workerInterestSalary = workerInterestSalary;
    }

    public Integer getWorkerInterestProductSalary() {
        return workerInterestProductSalary;
    }

    public void setWorkerInterestProductSalary(Integer workerInterestProductSalary) {
        this.workerInterestProductSalary = workerInterestProductSalary;
    }


    /**
     * Добавление в общий результат
     * */
    public void plus(Integer earnedMoney, Integer primeCostSum, Integer workerPeriodSalary, Integer workerInterestSalary, Integer workerInterestProductSalary) {
        this.earnedMoney += earnedMoney;
        this.primeCostSum += primeCostSum;
        this.workerPeriodSalary += workerPeriodSalary;
        this.workerInterestSalary += workerInterestSalary;
        this.workerInterestProductSalary += workerInterestProductSalary;
    }

}
