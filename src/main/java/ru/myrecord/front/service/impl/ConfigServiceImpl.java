package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ConfigDAO;
import ru.myrecord.front.data.model.entities.Config;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ConfigService;

@Service("configService")
public class ConfigServiceImpl implements ConfigService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("configDAO")
    private ConfigDAO configDAO;


    @Override
    public void add(Config config) {
        configDAO.save(config);
    }

    @Override
    public void update(Config config) {
        configDAO.save(config);
    }

    @Override
    public Config findByOwnerUser(User ownerUser) {
        return configDAO.findByOwnerUser(ownerUser);
    }
}
