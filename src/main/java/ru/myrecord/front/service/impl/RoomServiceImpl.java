package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.RoomDAO;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.RoomService;

@Service("roomService")
public class RoomServiceImpl implements RoomService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("roomDAO")
    private RoomDAO roomDAO;

    @Override
    public Room findRoomById(Integer id) {
        return roomDAO.findById(id);
    }
}
