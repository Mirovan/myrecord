package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.data.model.User;

import java.util.List;
import java.util.Set;

public interface RoleService {
    //public User findUserByEmail(String email);
    Set<Role> findRolesByRoleName(List<String> roleNames);
}
