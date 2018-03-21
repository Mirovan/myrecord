package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserSalary;

public interface ClientRecordService {
    ClientRecord add(ClientRecord clientRecord, User ownerUser);
    ClientRecord findByUser(User user);
}
