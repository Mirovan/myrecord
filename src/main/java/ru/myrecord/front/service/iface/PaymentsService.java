package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Payment;
import ru.myrecord.front.data.model.entities.User;

import java.util.Set;

public interface PaymentsService {

    void addPayment(Payment payment);
    Set<Payment> getPayments(User user);
    int removePayment(Payment payment);

}
