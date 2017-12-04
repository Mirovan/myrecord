package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.Service;
import ru.myrecord.front.data.model.User;
import java.util.List;

public interface ServiceService {
    public Service findServiceById(Integer id);
    public List<Service> findByActive(User user);
    public void add(Service service);
    public void update(Service service);
}
