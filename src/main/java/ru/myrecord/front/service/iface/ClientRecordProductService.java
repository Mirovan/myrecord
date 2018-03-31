package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;

import java.time.LocalDate;
import java.util.Set;

public interface ClientRecordProductService {
    void add(ClientRecordProduct clientRecordProduct);
    ClientRecordProduct findByClientRecord(ClientRecord clientRecord);
    Set<ClientRecordProduct> findByDate(LocalDate date);
}
