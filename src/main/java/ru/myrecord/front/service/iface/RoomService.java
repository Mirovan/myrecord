package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.Service;
import ru.myrecord.front.data.model.User;

import java.util.List;
import java.util.Set;

public interface RoomService {
    public Room findRoomById(Integer id);
    public Set<Room> findRoomsByActive(User user);
    public List<Room> findAll();
    public void add(Room room);
    public void update(Room room);
}
