package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.Schedule;
import ru.myrecord.front.data.model.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

@Repository("scheduleDAO")
public interface ScheduleDAO extends JpaRepository<Schedule, Integer> {
	Schedule findById(Integer id);
	List<Schedule> findByUser(User user);
}