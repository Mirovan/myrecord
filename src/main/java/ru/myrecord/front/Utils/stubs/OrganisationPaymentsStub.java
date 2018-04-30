package ru.myrecord.front.Utils.stubs;

import java.time.LocalDate;
import java.util.List;

public class OrganisationPaymentsStub {

    private List<PaymentsStub> payments;
    private LocalDate expDate;
    private Float balance;

    public List<PaymentsStub> getPayments() {
        return payments;
    }

    public void setPayments(List<PaymentsStub> payments) {
        this.payments = payments;
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
}
