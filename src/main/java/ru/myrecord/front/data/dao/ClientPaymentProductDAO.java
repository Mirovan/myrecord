package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientPaymentProduct;

@Repository("clientPaymentProductDAO")
public interface ClientPaymentProductDAO extends JpaRepository<ClientPaymentProduct, Integer> {
}