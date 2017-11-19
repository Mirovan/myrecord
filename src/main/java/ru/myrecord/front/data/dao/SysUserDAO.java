package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.SysUser;

@Repository("sysUserDAO")
public interface SysUserDAO extends JpaRepository<SysUser, Long> {
//    void add(SysUser sysUser);
//    SysUser getById(int id);
//    SysUser getByAuthPhone(String phone, String pass);
//    SysUser getByAuthEmail(String email, String pass);
//    void delete(int id);
//    SysUser findByPhone(String phone);
    SysUser findByEmail(String email);

}