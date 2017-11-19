package ru.myrecord.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.SysUserDAO;
import ru.myrecord.front.data.model.SysUser;

import java.util.ArrayList;
import java.util.List;

//@Qualifier("userService")
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("sysUserDAO")
    private SysUserDAO sysUserDAO;


//    @Override
//    public void add(SysUser element) {
//    }

    @Override
    public List<SysUser> getAll() {
        List<SysUser> list = new ArrayList<>();

        SysUser sysUser = new SysUser();
        sysUser.setId(new Long(1));
        sysUser.setEmail("zzzzz@asdasd.ru");
        list.add(sysUser);

        sysUser = new SysUser();
        sysUser.setId(new Long(2));
        sysUser.setEmail("xxxxxxxxx@dddddddddddd.ru");
        list.add(sysUser);

        return list;
    }

//    @Override
//    public SysUser getByAuthPhone(String phone, String pass) {
//        //return sysUserDAO.getByAuthPhone(phone, pass);
//        return null;
//    }

    @Override
    public SysUser getByAuthEmail(String email, String pass) {
        //return sysUserDAO.getByAuthEmail(email, pass);
        return null;
    }
}
