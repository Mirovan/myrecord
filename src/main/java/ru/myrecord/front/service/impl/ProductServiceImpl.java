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
import ru.myrecord.front.service.iface.ProductService;

import java.util.Set;

@Service("productService")
public class ProductServiceImpl implements ProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("productDAO")
    private ProductDAO productDAO;

    @Override
    public Product findProductById(Integer id) {
        return productDAO.findById(id);
    }

//    @Override
//    public Set<Product> findProductsByUser(User ownerUser) {
//        //return productDAO.findByOwnerUserAndActiveTrueOrderByIdAsc(ownerUser);
//        return null;
//    }

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

}
