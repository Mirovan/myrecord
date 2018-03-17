package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;

import java.util.List;
import java.util.Set;

@Repository("userProductDAO")
public interface UserProductDAO extends JpaRepository<UserProduct, Integer> {
    Set<UserProduct> findByUserAndProductAndActiveTrue(User user, Product product);
    Set<UserProduct> findByUserAndProduct(User user, Product product);
}