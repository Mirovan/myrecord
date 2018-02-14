package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.UserProductDAO;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.UserProductService;

@Service("userRoomService")
public class UserProductServiceImpl implements UserProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userProductDAO")
    UserProductDAO userProductDAO;

    @Override
    public void add(UserProduct userProduct) {
        userProductDAO.save(userProduct);
    }

    @Override
    public void update(UserProduct userProduct) {

    }
}
