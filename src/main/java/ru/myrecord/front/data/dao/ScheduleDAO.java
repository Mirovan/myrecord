package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Repository("scheduleDAO")
public interface ScheduleDAO extends JpaRepository<Schedule, Integer> {
	Schedule findByWorkerAndSdate(User worker, LocalDate date);
	List<Schedule> findByWorker(User worker);
	@Transactional
	void deleteByWorkerAndSdate(User worker, LocalDate date);
	Set<Schedule> findBySdate(LocalDate date);
}