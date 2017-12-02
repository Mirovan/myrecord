package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.Service;
import ru.myrecord.front.data.model.User;

import java.util.List;

@Repository("serviceDAO")
public interface ServiceDAO extends JpaRepository<Service, Long> {
	Service findById(Long id);
	List<Service> findByUserAndActiveTrueOrderByIdAsc(User user);
}
