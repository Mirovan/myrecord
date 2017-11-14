package ru.myrecord.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.myrecord.front.data.repository.SysUserRepository;
import ru.myrecord.front.data.model.SysUser;
import java.util.List;

public class SysUserServiceImpl implements SysUserService {

    private static final Logger LOG = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserRepository sysUserRepository;


    @Override
    public void add(SysUser element) {
    }

    @Override
    public List<SysUser> getAll() {
        return null;
    }

    @Override
    public SysUser getByAuthPhone(String phone, String pass) {
        return sysUserRepository.getByAuthPhone(phone, pass);
    }

    @Override
    public SysUser getByAuthEmail(String email, String pass) {
        return sysUserRepository.getByAuthEmail(email, pass);
    }
}
