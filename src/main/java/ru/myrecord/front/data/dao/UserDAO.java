package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.User;

@Repository("userDAO")
public interface UserDAO extends JpaRepository<User, Long> {
    User findByEmail(String email);
}