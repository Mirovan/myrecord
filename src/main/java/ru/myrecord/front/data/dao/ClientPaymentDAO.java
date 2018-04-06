package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.data.model.entities.ClientRecord;

import java.util.List;

@Repository("clientPaymentDAO")
public interface ClientPaymentDAO extends JpaRepository<ClientPayment, Integer> {
    ClientPayment findById(Integer id);
}