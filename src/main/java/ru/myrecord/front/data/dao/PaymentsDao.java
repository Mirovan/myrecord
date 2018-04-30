package ru.myrecord.front.data.dao;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Payment;
import ru.myrecord.front.data.model.entities.User;

import java.util.Set;

@Repository("paymentsDAO")
public interface PaymentsDao extends JpaRepository<Payment, Integer> {

    Payment findById(Integer id);
    Set<Payment> findByUser(User user);

}
