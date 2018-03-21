package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;
import java.util.Set;

@Repository("roomDAO")
public interface RoomDAO extends JpaRepository<Room, Integer> {
	Room findById(Integer id);
	Set<Room> findByOwnerUserAndActiveTrueOrderByIdAsc(User ownerUser);
	List<Room> findAll();
}
