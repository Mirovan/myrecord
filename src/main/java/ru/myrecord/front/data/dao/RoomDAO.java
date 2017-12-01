package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;

import java.util.List;
import java.util.Set;

@Repository("roomDAO")
public interface RoomDAO extends JpaRepository<Room, Integer> {
	Room findById(Long id);
	//List<Room> findByActiveTrueOrderByIdAsc();
	List<Room> findByUserAndActiveTrueOrderByIdAsc(User user);
}
