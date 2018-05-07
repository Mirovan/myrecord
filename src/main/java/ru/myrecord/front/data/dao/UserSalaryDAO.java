package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserSalary;

import java.util.Set;

@Repository("userSalaryDAO")
public interface UserSalaryDAO extends JpaRepository<UserSalary, Integer> {
    Set<UserSalary> findByWorkerOrderByStartdateDesc(User worker);
}