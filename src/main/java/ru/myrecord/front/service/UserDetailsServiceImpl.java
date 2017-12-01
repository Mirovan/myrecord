package ru.myrecord.front.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.RoleDAO;
import ru.myrecord.front.data.dao.UserDAO;
import ru.myrecord.front.data.model.Role;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.impl.UserServiceImpl;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    @Qualifier("userDAO")
    UserDAO userDAO;

    @Autowired
    @Qualifier("roleDAO")
    RoleDAO roleDAO;

    @Autowired
    UserServiceImpl userService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        //User user = userDAO.findByEmail(email);
        User user = userService.findUserByEmail(email);
        if (user == null) {
            //ToDo: пробуем найти юзера по телефону
            throw new UsernameNotFoundException("user not found");
        } else {
            Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
            for (Role role : user.getRoles()) {
                grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
            }
            return new org.springframework.security.core.userdetails.User(
                    email,
                    user.getPass(),
                    grantedAuthorities
            );
        }
    }

}
