package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.entities.Role;

import java.util.List;
import java.util.Set;

public interface RoleService {
    Role findRoleById(Integer id);
    Set<Role> findRolesByRoleName(List<String> roleNames);
}
