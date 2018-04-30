package ru.myrecord.front.service.iface.organisation;

import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.data.model.entities.User;

import java.util.Set;

public interface PaymentsService {

    void addPayment(Payment payment);
    Set<Payment> getPayments(User user);
    int removePayment(Payment payment);

}
