package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;

import java.util.List;

public interface UserService {
    public User findUserByEmail(String email);
    public void addSysUser(User user);
    public void addSimpleUser(User user);
    public void update(User user);
    public List<User> findByActive(User user);
}
