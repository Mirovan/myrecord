package ru.myrecord.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.SysUserDAO;
import ru.myrecord.front.data.model.SysUser;

import java.util.ArrayList;
import java.util.List;

//@Qualifier("userService")
@Service("sysUserService")
public class SysUserServiceImpl implements SysUserService {

    private static final Logger LOG = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    @Qualifier("sysUserDAO")
    private SysUserDAO sysUserDAO;


    @Override
    public void add(SysUser element) {
    }

    @Override
    public List<SysUser> getAll() {
        List<SysUser> list = new ArrayList<>();

        SysUser sysUser = new SysUser();
        sysUser.setId(1);
        sysUser.setEmail("zzzzz@asdasd.ru");
        list.add(sysUser);


        sysUser.setId(2);
        sysUser.setEmail("xxxxxxxxx@dddddddddddd.ru");
        list.add(sysUser);

        return list;
    }

    @Override
    public SysUser getByAuthPhone(String phone, String pass) {
        return sysUserDAO.getByAuthPhone(phone, pass);
    }

    @Override
    public SysUser getByAuthEmail(String email, String pass) {
        return sysUserDAO.getByAuthEmail(email, pass);
    }
}
