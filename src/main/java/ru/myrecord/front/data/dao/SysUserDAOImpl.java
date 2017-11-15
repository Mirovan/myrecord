package ru.myrecord.front.data.dao;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import ru.myrecord.front.data.model.SysUser;

@Component("sysUserDAO")
public class SysUserDAOImpl implements SysUserDAO {

    @Override
    public void add(SysUser sysUser) {

    }

    @Override
    public SysUser getById(int id) {
        return null;
    }

    @Override
    public SysUser getByAuthPhone(String phone, String pass) {
        return null;
    }

    @Override
    public SysUser getByAuthEmail(String email, String pass) {
        return null;
    }

    @Override
    public void delete(int id) {

    }
}
