package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.UserRoomDAO;
import ru.myrecord.front.data.model.entities.UserRoom;
import ru.myrecord.front.service.iface.UserRoomService;

@Service("userRoomService")
public class UserRoomServiceImpl implements UserRoomService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userRoomDAO")
    UserRoomDAO userRoomDAO;

    @Override
    public void add(UserRoom userRoom) {
        userRoomDAO.save(userRoom);
    }

    @Override
    public void update(UserRoom userRoom) {

    }
}
