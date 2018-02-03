package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserService;

@Repository("userServiceDAO")
public interface UserServiceDAO extends JpaRepository<UserService, Integer> {
}