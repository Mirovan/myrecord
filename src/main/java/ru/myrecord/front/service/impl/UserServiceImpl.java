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
    public void add(User user) {
        user.setPass(bCryptPasswordEncoder.encode("000000")); //ToDo: make random password
//        user.setActive(1);
        Role userRole = roleDAO.findByRole("ADMIN");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userDAO.save(user);
    }

//    @Override
//    public void add(SysUser element) {
//    }

//    @Override
//    public List<SysUser> getAll() {
//        List<SysUser> list = new ArrayList<>();
//
//        SysUser sysUser = new SysUser();
//        sysUser.setId(new Long(1));
//        sysUser.setEmail("zzzzz@asdasd.ru");
//        list.add(sysUser);
//
//        sysUser = new SysUser();
//        sysUser.setId(new Long(2));
//        sysUser.setEmail("xxxxxxxxx@dddddddddddd.ru");
//        list.add(sysUser);
//
//        return list;
//    }

//    @Override
//    public SysUser getByAuthPhone(String phone, String pass) {
//        //return userDAO.getByAuthPhone(phone, pass);
//        return null;
//    }

//    @Override
//    public SysUser getByAuthEmail(String email, String pass) {
//        //return userDAO.getByAuthEmail(email, pass);
//        return null;
//    }
}
