package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.User;

import java.util.List;

@Repository("userDAO")
public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    User findById(Integer id);
    List<User> findByOwnerUserAndActiveTrueOrderByIdAsc(User user);
}