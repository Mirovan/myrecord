package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.Role;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;

import java.security.Principal;
import java.util.List;
import java.util.Set;

public interface UserService {
    public User findUserByEmail(String email);
    public void addSysUser(User user);
    public void addSimpleUser(User user);
    public void update(User user);
    public Set<User> findUsersByOwner(User ownerUser);
    public Set<User> findUsersByRoom(Room room);
    public User findUserById(Integer id);
    //public Set<User> findUsersByUserRooms(Set<UserRoom> userRooms);
    Set<UserAdapter> getUserAdapterCollection(Set<User> users);
    Set<Role> getRolesForSimpleUser();
    Boolean userEquals(Integer userId1, Integer userId2);
    Boolean hasUser(Integer ownerUserId, Integer childUser);
    Boolean hasUser(Principal principal, Integer childUserId);
    Boolean hasRoom(Integer ownerUserId, Integer roomId);
    Boolean hasRoom(Principal principal, Integer roomId);
    Boolean hasProduct(Integer ownerUserId, Integer serviceId);
    Boolean hasProduct(Principal principal, Integer serviceId);
    Boolean hasProducts(Principal principal, List<Integer> products);
    Boolean hasAccessToRoles(Principal principal, Set<Role> roles);
    String generatePassword(String password);
}
