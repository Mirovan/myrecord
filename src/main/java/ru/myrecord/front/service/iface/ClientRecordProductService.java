package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.adapters.UserProductAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface ClientRecordProductService {
    void add(ClientRecordProduct clientRecordProduct);
    ClientRecordProduct findByClientRecord(ClientRecord clientRecord);
    Set<ClientRecordProduct> findByDate(User ownerUser, LocalDate date);
    List<UserProductAdapter> getClientsByDate(User ownerUser, LocalDate date);  //Поиск клиентов кто записан на определенную дату
    List<UserProductAdapter> getRemindClientsByDate(User ownerUser, LocalDate date);  //Поиск клиентов кто уже приходил к нам на услуги n-дней назад
}
