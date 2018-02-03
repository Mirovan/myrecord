package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import java.util.Set;

public interface ProductService {
    public Product findServiceById(Integer id);
    public Set<Product> findServicesByUser(User user);
    public Set<Product> findServicesByRoom(Room room);
    public void add(Product product);
    public void update(Product product);
}
