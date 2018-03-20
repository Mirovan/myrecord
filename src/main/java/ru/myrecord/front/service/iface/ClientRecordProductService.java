package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.User;

public interface ClientRecordProductService {
    void add(ClientRecordProduct clientRecordProduct);
    ClientRecordProduct findByClientRecord(ClientRecord clientRecord);
}
