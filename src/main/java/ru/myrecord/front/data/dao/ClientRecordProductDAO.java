package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.User;

import java.time.LocalDateTime;
import java.util.Set;

@Repository("clientRecordProductDAO")
public interface ClientRecordProductDAO extends JpaRepository<ClientRecordProduct, Integer> {
    Set<ClientRecordProduct> findBySdateBetweenAndWorkerIn(LocalDateTime start, LocalDateTime end, Set<User> workers);
}