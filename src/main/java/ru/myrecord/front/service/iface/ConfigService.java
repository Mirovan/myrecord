package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Config;

public interface ConfigService {
    void add(Config config);
    void update(Config config);
}
