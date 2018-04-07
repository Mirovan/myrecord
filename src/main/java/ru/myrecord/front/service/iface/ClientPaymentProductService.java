package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.data.model.entities.ClientPaymentProduct;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ClientPaymentProductService {
    ClientPaymentProduct add(ClientPaymentProduct clientPaymentProduct);
    List<ClientPaymentProduct> findByPaymentsAndWorkers(List<ClientPayment> clientPayments);
}
