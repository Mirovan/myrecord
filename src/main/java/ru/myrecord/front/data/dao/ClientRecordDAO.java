package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientRecord;

@Repository("clientRecordDAO")
public interface ClientRecordDAO extends JpaRepository<ClientRecord, Integer> {
}