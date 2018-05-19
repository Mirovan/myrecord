package ru.myrecord.front.data.dao.organisation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.data.model.entities.User;

import java.util.Set;

@Repository("paymentsDAO")
public interface PaymentsDAO extends JpaRepository<Payment, Integer> {

    //Payment findById(Integer id);
    Set<Payment> findByUser(User user);

}
