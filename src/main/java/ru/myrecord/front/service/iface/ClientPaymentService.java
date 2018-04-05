package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientPayment;

public interface ClientPaymentService {
    ClientPayment add(ClientPayment clientPayment);
    ClientPayment update(ClientPayment clientPayment);
}
