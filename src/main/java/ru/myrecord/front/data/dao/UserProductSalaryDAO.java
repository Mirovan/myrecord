package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProductSalary;
import ru.myrecord.front.data.model.entities.UserSalary;

import java.util.Set;

@Repository("userProductSalaryDAO")
public interface UserProductSalaryDAO extends JpaRepository<UserProductSalary, Integer> {
    Set<UserProductSalary> findByWorkerAndProductOrderByStartdateDesc(User worker, Product product);
}