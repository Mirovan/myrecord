package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientPayment;
import ru.myrecord.front.data.model.entities.ClientPaymentProduct;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;
import java.util.Set;

@Repository("clientPaymentProductDAO")
public interface ClientPaymentProductDAO extends JpaRepository<ClientPaymentProduct, Integer> {
}