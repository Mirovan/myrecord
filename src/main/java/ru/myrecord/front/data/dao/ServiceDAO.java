package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.Service;
import ru.myrecord.front.data.model.entities.User;

import java.util.Set;

@Repository("serviceDAO")
public interface ServiceDAO extends JpaRepository<Service, Integer> {
	Service findById(Integer id);
	Set<Service> findByUserAndActiveTrueOrderByIdAsc(User user);
	Set<Service> findByRoomAndActiveTrueOrderByIdAsc(Room room);
}
