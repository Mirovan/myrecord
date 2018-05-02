package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;

import java.util.Set;

@Repository("userProductDAO")
public interface UserProductDAO extends JpaRepository<UserProduct, Integer> {
    Set<UserProduct> findByWorkerAndProductAndActiveTrue(User worker, Product product);
    Set<UserProduct> findByWorkerAndProduct(User worker, Product product);
    Set<UserProduct> findByProductAndActiveTrue(Product product);
    Set<UserProduct> findByProduct(Product product);
    Set<UserProduct> findByWorkerAndActiveTrue(User worker);
}