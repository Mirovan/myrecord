package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Config;
import ru.myrecord.front.data.model.entities.User;

public interface ConfigService {
    void add(Config config);
    void update(Config config);
    Config findByOwnerUser(User ownerUser);
}
