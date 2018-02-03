package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.RoomDAO;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.RoomService;

import java.util.List;
import java.util.Set;

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


    @Override
    public Set<Room> findRoomsByActive(User user) {
        return roomDAO.findByUserAndActiveTrueOrderByIdAsc(user);
    }

    @Override
    public List<Room> findAll() {
        return roomDAO.findAll();
    }


//    @Override
//    public Set<ru.myrecord.front.data.model.entities.Product> findServicesByRoom(Room room) {
//        return null;//roomDAO.findByRoom(room);
//    }

    @Override
    public void add(Room room) {
        roomDAO.save(room);
    }


    @Override
    public void update(Room room) {
        roomDAO.save(room);
    }

}
