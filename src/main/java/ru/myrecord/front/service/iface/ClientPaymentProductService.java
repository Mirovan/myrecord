package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.data.model.entities.ClientPaymentProduct;

import java.util.List;

public interface ClientPaymentProductService {
    ClientPaymentProduct add(ClientPaymentProduct clientPaymentProduct);
    List<ClientPaymentProduct> findByPaymentsAndWorkers(List<ClientPayment> clientPayments);
}
