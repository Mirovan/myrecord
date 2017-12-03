package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrecord.front.data.dao.RoleDAO;
import ru.myrecord.front.data.dao.UserDAO;
import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.UserService;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("roleDAO")
    private RoleDAO roleDAO;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            int size = user.getRoles().size();  //Для LAZY hibernate initialization
        }
        return user;
    }

    @Override
    public void addSysUser(User user) {
        user.setPass(bCryptPasswordEncoder.encode("000000")); //ToDo: make random password
//        user.setActive(1);
        Role userRole = roleDAO.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userDAO.save(user);
    }

    @Override
    public void addSimpleUser(User user) {

    }

    @Override
    public void update(User user) {

    }

    @Override
    public List<User> findByActive(User user) {
        return null;
    }
}
