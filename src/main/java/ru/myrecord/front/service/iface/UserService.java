package ru.myrecord.front.service.iface;


import ru.myrecord.front.data.model.enums.UserRoles;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Role;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface UserService {
    public User findUserByEmail(String email);
    public void addSysUser(User ownerUser);
    public void addSimpleUser(User user);
    public void update(User user);
    Set<User> findUsersByOwner(User ownerUser);  //Поиск всех пользователей для ownerUser
    Set<User> findWorkersByOwner(User ownerUser);  //Поиск всех работников для ownerUser
    Set<User> findClientsByOwner(User ownerUser);  //Поиск всех работников для ownerUser
    Set<User> findByRole(UserRoles userRoles);  //Поиск всех пользователей с ролью SysUser
    public Set<User> findUsersByRoom(Room room);
    public User findUserById(Integer id);
    //public Set<User> findUsersByUserRooms(Set<UserRoom> userRooms);
    Set<UserAdapter> getUserAdapterCollection(Set<User> users);
    UserAdapter getUserAdapter(User user);
    Set<Role> getRolesForSimpleUser();
    Boolean userEquals(Integer userId1, Integer userId2);
    Boolean hasUser(Integer ownerUserId, Integer childUser);
    Boolean hasUser(Principal principal, Integer childUserId);
    Boolean hasRoom(Integer ownerUserId, Integer roomId);
    Boolean hasRoom(Principal principal, Integer roomId);
    Boolean hasProduct(Integer ownerUserId, Integer productId);
    Boolean hasProduct(Principal principal, Integer productId);
    Boolean hasProducts(Principal principal, List<Integer> products);
    Boolean hasAccessToRoles(Principal principal, Set<Role> roles);
    String generatePassword(String password);
    Set<User> findWorkersByDate(LocalDate date, User ownerUser);   //Поиск всех мастеров кто работает в этот день
    Set<User> findWorkersByDateAndProduct(LocalDate date, Product product, User ownerUser);   //Поиск всех мастеров кто работает в этот день и кто оказывает услугу
    Boolean isWorkerDoProduct(User user, Integer productId);    //Оказывает ли пользователь данную услугу
    Set<User> findWorkersByProduct(Product product);    //Поиск раюотника по услуге
}
