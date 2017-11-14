package ru.myrecord.front.data.repository;

import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.SysUser;

/**
 * Created by max on 13.11.2017.
 */

@Repository("sysUserRepository")
public interface SysUserRepository {
    void add(SysUser sysUser);
    SysUser getById(int id);
    SysUser getByAuthPhone(String phone, String pass);
    SysUser getByAuthEmail(String email, String pass);
    void delete(int id);
}