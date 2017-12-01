package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;

import java.util.List;

public interface RoomService {
    public Room findRoomById(Long id);
    public List<Room> findByActive(User user);
    public void add(Room room);
    public void update(Room room);
}
