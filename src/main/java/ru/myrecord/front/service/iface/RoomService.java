package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;
import java.util.Set;

public interface RoomService {
    Room findRoomById(Integer id);
    Set<Room> findRoomsByActive(User user);
    List<Room> findAll();
    void add(Room room);
    void update(Room room);
}
