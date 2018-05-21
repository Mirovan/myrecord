package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ProductDAO;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.UserProductService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;

    @Autowired
    private RoomService roomService;

    @Autowired
    private UserProductService userProductService;

    @Override
    public Product findProductById(Integer id) {
        if (id == null)
            return null;
        else
            return productDAO.findById(id).get();
    }

    @Override
    public Set<Product> findProductsByRoom(Room room) {
        return productDAO.findByRoomAndActiveTrueOrderByIdAsc(room);
    }

    @Override
    public void add(Product product) {
        productDAO.save(product);
    }

    @Override
    public void update(Product product) {
        productDAO.save(product);
    }

    @Override
    public Set<Product> findProductsByOwnerUser(User ownerUser) {
        List<Room> rooms = roomService.findRoomsByActive(ownerUser);
        return productDAO.findByRoomInAndActiveTrueOrderByIdAsc(rooms);
    }

    @Override
    public Set<Product> findProductsByWorker(User worker) {
        Set<UserProduct> userProducts = userProductService.findByUserActiveLink(worker);
        Set<Product> products = new HashSet<>();
        for (UserProduct up : userProducts) {
            products.add(up.getProduct());
        }
        return products;
    }
}
