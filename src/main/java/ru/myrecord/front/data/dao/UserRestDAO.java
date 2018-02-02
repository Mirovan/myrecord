//package ru.myrecord.front.data.dao;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.rest.core.annotation.RepositoryRestResource;
//import ru.myrecord.front.data.model.User;
//
//import java.util.List;
//
//@RepositoryRestResource(collectionResourceRel = "rest/users", path = "rest/users")
//public interface UserRestDAO extends JpaRepository<User, Integer> {
//    List<User> findByOwnerUserAndActiveTrueOrderByIdAsc(User user);
//}