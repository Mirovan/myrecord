package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.Role;

import java.util.List;
import java.util.Set;

@Repository("roleDAO")
public interface RoleDAO extends JpaRepository<Role, Integer> {
	Role findByRole(String role);
	@Query(value = "select u.email, r.role from users u inner join user_role ur on(u.id=ur.user_id) inner join role r on(ur.role_id=r.id) where u.email=?",
            nativeQuery = true)
    Set<Role> findRoleByEmail(String email);
	Set<Role> findByRoleIn(List<String> roleNames);
}
