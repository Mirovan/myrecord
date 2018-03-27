package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.myrecord.front.data.dao.RoleDAO;
import ru.myrecord.front.data.dao.UserDAO;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.*;

@Service("userService")
public class UserServiceImpl implements UserService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userDAO")
    private UserDAO userDAO;

    @Autowired
    @Qualifier("roleDAO")
    private RoleDAO roleDAO;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        User user = userDAO.findByEmail(email);
        if (user != null) {
            int size = user.getRoles().size();  //Для LAZY hibernate initialization
        }
        return user;
    }


    @Override
    public void addSysUser(User user) {
        user.setPass(bCryptPasswordEncoder.encode("000000")); //ToDo: make random password
        Role userRole = roleDAO.findByRole("SYSUSER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        userDAO.save(user);
    }


    @Override
    public void addSimpleUser(User user) {
        userDAO.save(user);
    }


    @Override
    public void update(User user) {
        userDAO.save(user);
    }


    @Override
    public Set<User> findUsersByOwner(User ownerUser) {
        return userDAO.findByOwnerUserAndActiveTrueOrderByIdAsc(ownerUser);
    }


    @Override
    public User findUserById(Integer id) {
        User user = userDAO.findById(id);
        if (user != null) {
            int size = user.getRoles().size();  //Для LAZY hibernate initialization
        }
        return user;
    }

//    @Override
//    public Set<User> findUsersByRoom(Room room) {
//        return userDAO.findByRoomId(room.getId());
//    }

    @Override
    public Set<UserAdapter> getUserAdapterCollection(Set<User> users) {
        Set<UserAdapter> userAdapterColection = new HashSet<>();
        for (User user: users) {
            userAdapterColection.add(new UserAdapter(user));
        }
        return userAdapterColection;
    }


    /**
     * Получение ролей для системного пользователя
     * */
    @Override
    public Set<Role> getRolesForSimpleUser() {
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("MASTER");
        roleNames.add("MANAGER");
        Set<Role> roles = roleService.findRolesByRoleName( roleNames );
        return roles;
    }


    /**
     * Проверка - равны ли пользователи
     * */
    @Override
    public Boolean userEquals(Integer userId1, Integer userId2) {
        return userId1.equals(userId2);
    }


    /**
     * Проверка - принадлежит ли системному пользователю обычный пользователь
     * */
    @Override
    public Boolean hasUser(Integer ownerUserId, Integer childUserId) {
        User childUser = findUserById(childUserId);
        if ( ownerUserId.equals(childUser.getOwnerUser().getId()) && childUser.getActive())
            return true;
        else
            return false;
    }


    /**
     * Проверка - принадлежит ли системному пользователю обычный пользователь
     * */
    @Override
    public Boolean hasUser(Principal principal, Integer childUserId) {
        User ownerUser = findUserByEmail(principal.getName());
        return hasUser(ownerUser.getId(), childUserId);
    }

    /**
     * Проверка - принадлежит ли системному пользователю данное помещение
     * */
    @Override
    public Boolean hasRoom(Integer ownerUserId, Integer roomId) {
        Room room = roomService.findRoomById(roomId);
        if ( ownerUserId.equals(room.getOwnerUser().getId()) && room.getActive() )
            return true;
        else
            return false;
    }


    /**
     * Проверка - принадлежит ли системному пользователю данное помещение
     * */
    @Override
    public Boolean hasRoom(Principal principal, Integer roomId) {
        User ownerUser = findUserByEmail(principal.getName());
        return hasRoom(ownerUser.getId(), roomId);
    }


    /**
     * Проверка - принадлежит ли системному пользователю данная услуга
     * */
    @Override
    public Boolean hasProduct(Integer ownerUserId, Integer productId) {
        Product product = productService.findProductById(productId);
        Room room = product.getRoom();
        if ( product.getActive() && room.getActive() && hasRoom(ownerUserId, room.getId()) )
            return true;
        else
            return false;
    }


    /**
     * Проверка - принадлежит ли системному пользователю данная услуга
     * */
    @Override
    public Boolean hasProduct(Principal principal, Integer productId) {
        User ownerUser = findUserByEmail(principal.getName());
        Product product = productService.findProductById(productId);
        Room room = product.getRoom();
        if ( product.getActive() && room.getActive() && hasRoom(principal, room.getId()) )
            return true;
        else
            return false;
    }


    /**
     * Проверка - принадлежат ли продукты/услуги системному пользователю
     * */
    @Override
    public Boolean hasProducts(Principal principal, List<Integer> products) {
        User ownerUser = findUserByEmail(principal.getName());
        for (Integer productId: products) {
            if ( !hasProduct(ownerUser.getId(), productId) )
                return false;
        }
        return true;
    }


    /**
     * Имеет ли пользователь возможность добавлять пользователь с определенными ролями
     * (системный поьлзователь имеет доступ к MASTER и MANAGER)
     * (Админ сайта имеет доступ ко всем ролям)
     * */
    @Override
    public Boolean hasAccessToRoles(Principal principal, Set<Role> roles) {
        User ownerUser = findUserByEmail(principal.getName());
        Set<Role> rolesAvailable = null;
        if ( ownerUser.getRoles().contains(roleService.findRoleByName("SYSUSER")) ) {         //Выбираем роли для системного пользователя
            rolesAvailable = getRolesForSimpleUser();
        } else if ( ownerUser.getRoles().contains(roleService.findRoleByName("ADMIN")) ) {    //Выбираем роли для админа
            //ToDo: это пока не реализовано
        }
        if ( rolesAvailable.containsAll(roles) )
            return true;
        else
            return false;
    }


    @Override
    public Set<User> findUsersByRoom(Room room) {
        Set<User> users = new HashSet<>();
        //Ищем все продукты в этой комнате
        Set<Product> products = productService.findProductsByRoom(room);
        //Ищем всех пользователей у которых есть данный продукт
        for (Product product : products) {
            //Определяем оказывает ли пользователь эту услугу
            Set<UserProduct> userProducts = userProductService.findByProductActiveLink(product);
            for (UserProduct userProduct: userProducts) {
                users.add( userProduct.getUser() );
            }
        }
        return users;
    }


    @Override
    public String generatePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }


    @Override
    public Set<User> findMastersByScheduleDay(LocalDate date, User ownerUser) {
        Set<User> res = new HashSet<>();
        Set<Schedule> scheduleSet = scheduleService.findByDate(date);
        for (Schedule schedule : scheduleSet ) {
            User user = schedule.getUser();
            if ( hasUser(ownerUser.getId(), user.getId()) )
                res.add( user );
        }
        return res;
    }

}
