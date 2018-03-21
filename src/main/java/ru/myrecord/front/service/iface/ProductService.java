package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import java.util.Set;

public interface ProductService {
    void add(Product product);
    void update(Product product);
    Product findProductById(Integer id);
    Set<Product> findProductsByRoom(Room room);
    Set<Product> findProductsByOwnerUser(User user);
}
