package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.ClientRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository("clientRecordDAO")
public interface ClientRecordDAO extends JpaRepository<ClientRecord, Integer> {
    Set<ClientRecord> findByDate(LocalDate date); //Поиск всех записей клиентов по дате
    ClientRecord findById(Integer id);  //Поиск клиентской записи по Id
    List<ClientRecord> findByDateBetweenAndActiveTrue(LocalDate from, LocalDate to);   //Поиск записей по дате
}