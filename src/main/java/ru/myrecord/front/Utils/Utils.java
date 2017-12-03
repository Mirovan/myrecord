package ru.myrecord.front.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.service.iface.RoleService;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by sbt-milovanov-mm on 01.12.2017.
 */
public class Utils {

    @Autowired
    private RoleService roleService;

    /**
     * Приоверка - равны ли пользователи
     * */
    public static Boolean userEquals(Long userId1, Long userId2) {
        return userId1.equals(userId2);
    }

    //ToDO: как вызвать сервис из статичного метода
//    public Set<Role> getRolesForSysUser() {
//        List<String> roleNames = new ArrayList<String>();
//        roleNames.add("MASTER");
//        roleNames.add("MANAGER");
//        Set<Role> roles = roleService.findRolesByRoleName( roleNames );
//        return roles;
//    }
}
