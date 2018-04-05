package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientPaymentDAO;
import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.service.iface.ClientPaymentService;

@Service("clientPaymentService")
public class ClientPaymentServiceImpl implements ClientPaymentService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("clientPaymentDAO")
    private ClientPaymentDAO clientPaymentDAO;

    @Override
    public ClientPayment add(ClientPayment clientPayment) {
        return clientPaymentDAO.save(clientPayment);
    }

    @Override
    public ClientPayment update(ClientPayment clientPayment) {
        return clientPaymentDAO.save(clientPayment);
    }
}
