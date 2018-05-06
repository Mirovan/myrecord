package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Config;
import ru.myrecord.front.data.model.entities.User;

@Repository("configDAO")
public interface ConfigDAO extends JpaRepository<Config, Integer> {
	Config findById(Integer id);
	Config findByOwnerUser(User ownerUser);
}
