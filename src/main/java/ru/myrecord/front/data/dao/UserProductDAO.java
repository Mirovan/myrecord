package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.UserProduct;

@Repository("userProductDAO")
public interface UserProductDAO extends JpaRepository<UserProduct, Integer> {
}