package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;

@Repository("clientRecordProductdDAO")
public interface ClientRecordProductDAO extends JpaRepository<ClientRecordProduct, Integer> {
}