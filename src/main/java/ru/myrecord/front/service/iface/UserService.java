package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;

import java.util.List;
import java.util.Set;

public interface UserService {
    public User findUserByEmail(String email);
    public void addSysUser(User user);
    public void addSimpleUser(User user);
    public void update(User user);
    public Set<User> findUsersByOwner(User ownerUser);
    public User findUserById(Integer id);
    public Set<User> findUsersByRoom(Room room);

    Set<UserAdapter> getUserAdapterCollection(Set<User> users);
}
