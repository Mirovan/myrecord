package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.Role;
import ru.myrecord.front.data.model.entities.User;

import java.util.Set;

@Repository("userDAO")
public interface UserDAO extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    //User findById(Integer id);
    Set<User> findByOwnerUserAndActiveTrueOrderByIdAsc(User ownerUser);
    Set<User> findByRoles(Role role);
    //Set<User> findByUserroomIn(Set<UserRoom> userRooms);
    //Set<User> findByUserroomIn(Set<UserRoom> userRooms);

//    @Query(value = "SELECT u.* FROM users u " +
//            "INNER JOIN user_room ur ON u.id = ur.user_id " +
//            "WHERE ur.room_id = ?",
//            nativeQuery = true)
//    Set<User> findByRoomId(Integer roomId);
}