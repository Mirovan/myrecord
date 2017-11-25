package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.User;

public interface UserService {
    public User findUserByEmail(String email);
    public void saveUser(User user);
}
