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
    public ModelAndView users() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("cabinet/user/index");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.GET)
    public ModelAndView simpleUserAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");

        User user = new User();
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("MASTER");
        roleNames.add("MANAGER");
        Set<Role> roles = roleService.findRolesByRoleName( roleNames );
        user.setRoles(roles);
// TODO: 03.12.2017 - проверить добаквление пользователя, сделать view 
        modelAndView.addObject("roles", roles);
        modelAndView.addObject("user", user);
        modelAndView.setViewName("cabinet/user/edit");
        return modelAndView;
    }

    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.POST)
    public ModelAndView roomAddPost(User room, Principal principal) {
        User user = userService.findUserByEmail(principal.getName());
        room.setUser(user);
        room.setActive(true);
        userService.addSimpleUser(user);
        return new ModelAndView("redirect:/cabinet/users/");
    }

}
