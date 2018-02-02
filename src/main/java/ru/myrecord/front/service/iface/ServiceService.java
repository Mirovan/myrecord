package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.Service;
import ru.myrecord.front.data.model.entities.User;
import java.util.Set;

public interface ServiceService {
    public Service findServiceById(Integer id);
    public Set<Service> findServicesByUser(User user);
    public Set<Service> findServicesByRoom(Room room);
    public void add(Service service);
    public void update(Service service);
}
