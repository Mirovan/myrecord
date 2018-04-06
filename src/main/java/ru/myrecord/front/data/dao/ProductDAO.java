package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;
import java.util.Set;

@Repository("productDAO")
public interface ProductDAO extends JpaRepository<Product, Integer> {
	Product findById(Integer id);
	Set<Product> findByRoomAndActiveTrueOrderByIdAsc(Room room);
	Set<Product> findByRoomInAndActiveTrueOrderByIdAsc(List<Room> rooms);
}
