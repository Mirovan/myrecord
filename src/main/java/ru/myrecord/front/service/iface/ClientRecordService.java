package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserSalary;

public interface ClientRecordService {
    void add(ClientRecord clientRecord);
    ClientRecord findByUser(User user);
}
