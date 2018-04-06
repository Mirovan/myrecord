package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.data.model.entities.ClientRecord;

import java.util.List;

public interface ClientPaymentService {
    ClientPayment add(ClientPayment clientPayment);
    ClientPayment update(ClientPayment clientPayment);
    ClientPayment findById(Integer id);
    List<ClientPayment> findByRecords(List<ClientRecord> clientRecords);
}
