package ru.myrecord.front.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.RoleService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.*;


@Controller
public class CabinetUserController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView users(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "users", userService.findByActive(user) );
        modelAndView.setViewName("cabinet/user/index");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.GET)
    public ModelAndView simpleUserAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");

        User user = new User();
        Set<Role> roles = getRolesForSysUser();
        user.setRoles(roles);
        modelAndView.addObject("role", new Role()); //Пустая роль для добавления нового пользователя
        modelAndView.addObject("roles", roles); //Роли из БД
        modelAndView.addObject("user", user);
        modelAndView.setViewName("cabinet/user/edit");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.POST)
    public ModelAndView roomAddPost(User user, Long roleid, Principal principal) {
        User sysUser = userService.findUserByEmail(principal.getName());
        user.setOwnerUser(sysUser);

        //проверка - можем ли добавить данную роль для своего сотрудника
        Role role = roleService.findRoleById(roleid);
        Set<Role> rolesAvailable = getRolesForSysUser();
        //Проверка удачная - роль существует в списке доступных для этого системного пользователя
        if ( rolesAvailable.contains(role) ) {
            Set<Role> roles = new HashSet<Role>();
            roles.add(role);
            user.setRoles(roles);
            user.setActive(true);
            userService.addSimpleUser(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }

    public Set<Role> getRolesForSysUser() {
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("MASTER");
        roleNames.add("MANAGER");
        Set<Role> roles = roleService.findRolesByRoleName( roleNames );
        return roles;
    }

}
