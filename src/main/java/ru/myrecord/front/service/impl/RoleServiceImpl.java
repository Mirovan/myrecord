package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.RoleDAO;
import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.service.iface.RoleService;

import java.util.List;
import java.util.Set;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("roleDAO")
    private RoleDAO roleDAO;

    @Override
    public Set<Role> findRolesByRoleName(List<String> roleNames) {
        return roleDAO.findByRoleIn(roleNames);
    }
}
