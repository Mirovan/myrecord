package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientRecordProductDAO;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.service.iface.ClientRecordProductService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Service("clientRecordProductService")
public class ClientRecordProductServiceImpl implements ClientRecordProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("clientRecordProductDAO")
    private ClientRecordProductDAO clientRecordProductDAO;

    @Override
    public void add(ClientRecordProduct clientRecordProduct) {
        clientRecordProductDAO.save(clientRecordProduct);
    }

    @Override
    public ClientRecordProduct findByClientRecord(ClientRecord clientRecord) {
        return null;
    }

    @Override
    public Set<ClientRecordProduct> findByDate(LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
        LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
        return clientRecordProductDAO.findBySdateBetween(start, end);
    }
}
