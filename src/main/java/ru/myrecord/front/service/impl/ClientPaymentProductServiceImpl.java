package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientPaymentProductDAO;
import ru.myrecord.front.data.model.entities.ClientPaymentProduct;
import ru.myrecord.front.service.iface.ClientPaymentProductService;

@Service("clientPaymentProductService")
public class ClientPaymentProductServiceImpl implements ClientPaymentProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("clientPaymentProductDAO")
    private ClientPaymentProductDAO clientPaymentProductDAO;

    @Override
    public ClientPaymentProduct add(ClientPaymentProduct clientPaymentProduct) {
        return clientPaymentProductDAO.save(clientPaymentProduct);
    }
}
