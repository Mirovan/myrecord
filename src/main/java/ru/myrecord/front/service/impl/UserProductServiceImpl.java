package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.UserProductDAO;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;
import ru.myrecord.front.service.iface.UserProductService;

import java.util.Set;

@Service("userProductService")
public class UserProductServiceImpl implements UserProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userProductDAO")
    UserProductDAO userProductDAO;

    @Override
    public void add(UserProduct userProduct) {
        userProductDAO.save(userProduct);
    }

    @Override
    public void update(UserProduct userProduct) {
        userProductDAO.save(userProduct);
    }


    @Override
    public boolean hasUserProductActiveLink(User user, Product product) {
        for (UserProduct up : user.getUserProducts()) {
            if ( up.getProduct().getId().equals(product.getId()) && up.getActive() ) {
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean hasUserProductAnyLink(User user, Product product) {
        for (UserProduct up : user.getUserProducts()) {
            if ( up.getProduct().getId().equals(product.getId()) ) {
                return true;
            }
        }
        return false;
    }


    @Override
    public UserProduct findByUserAndProductActiveLink(User user, Product product) {
        Set<UserProduct> userProducts = userProductDAO.findByWorkerAndProductAndActiveTrue(user, product);
        //Должен быть только одно один элемент
        if (userProducts != null && userProducts.iterator().hasNext())
            return userProducts.iterator().next();
        else
            return null;
    }


    @Override
    public UserProduct findByUserAndProductAnyLink(User user, Product product) {
        Set<UserProduct> userProducts = userProductDAO.findByWorkerAndProduct(user, product);
        //Должен быть только одно один элемент
        if (userProducts != null && userProducts.iterator().hasNext())
            return userProducts.iterator().next();
        else
            return null;
    }

    @Override
    public Set<UserProduct> findByProductActiveLink(Product product) {
        return userProductDAO.findByProductAndActiveTrue(product);
    }

    @Override
    public Set<UserProduct> findByProductAnyLink(Product product) {
        return userProductDAO.findByProduct(product);
    }

    @Override
    public Set<UserProduct> findByUserActiveLink(User user) {
        return userProductDAO.findByWorkerAndActiveTrue(user);
    }
}
