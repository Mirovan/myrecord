package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientPaymentDAO;
import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.service.iface.ClientPaymentService;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public ClientPayment findById(Integer id) {
        if (id == null)
            return null;
        else
            return clientPaymentDAO.findById(id).get();
    }

    /**
     * Возвращаем объекты оплаты клиентов по записям
     * */
    @Override
    public List<ClientPayment> findByRecords(List<ClientRecord> clientRecords) {
        List<ClientPayment> list = new ArrayList<>();
        for (ClientRecord item : clientRecords) {
            if (item.getClientPayment() != null && item.getActive())
                list.add(item.getClientPayment());
        }
        return list;
    }
}
