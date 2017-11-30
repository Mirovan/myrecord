package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.Service;

@Repository("serviceDAO")
public interface ServiceDAO extends JpaRepository<Service, Integer> {
	Service findById(Long id);
}
