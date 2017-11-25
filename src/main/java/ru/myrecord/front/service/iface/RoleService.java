package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.User;

public interface RoleService {
    public User findUserByEmail(String email);
}
