package ru.myrecord.front.service;

import ru.myrecord.front.data.model.SysUser;

import java.util.List;

public interface SysUserService {
    void add(SysUser element);
    List<SysUser> getAll();
    SysUser getByAuthPhone(String phone, String pass);
    SysUser getByAuthEmail(String email, String pass);
}
