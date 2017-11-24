package ru.myrecord.front.service;


import ru.myrecord.front.data.model.User;

public interface RoleService {
    public User findUserByEmail(String email);
}
