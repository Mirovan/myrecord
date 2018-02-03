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
import ru.myrecord.front.data.model.entities.Role;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.RoleService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.ServiceService;
import ru.myrecord.front.service.iface.UserService;

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
    private ServiceService serviceService;

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
        Role userRole = roleDAO.findByRole("ADMIN");
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

    @Override
    public Set<User> findUsersByRoom(Room room) {
        return userDAO.findByRoomId(room.getId());
    }

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
    public Set<Role> getRolesForSysUser() {
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("MASTER");
        roleNames.add("MANAGER");
        Set<Role> roles = roleService.findRolesByRoleName( roleNames );
        return roles;
    }


    /**
     * Проверка - равны ли пользователи
     * */
    public Boolean userEquals(Integer userId1, Integer userId2) {
        return userId1.equals(userId2);
    }


    /**
     * Проверка - принадлежит ли системному пользователю обычный пользователь
     * */
    public Boolean hasUser(Integer ownerUserId, Integer childUserId) {
        User childUser = findUserById(childUserId);
        if ( ownerUserId.equals(childUser.getOwnerUser().getId()) && childUser.getActive())
            return true;
        else
            return false;
    }


    /**
     * Проверка - принадлежит ли системному пользователю данное помещение
     * */
    public Boolean hasRoom(Integer ownerUserId, Integer roomId) {
        Room room = roomService.findRoomById(roomId);
        if ( ownerUserId.equals(room.getUser().getId()) && room.getActive() )
            return true;
        else
            return false;
    }

    /**
     * Проверка - принадлежит ли системному пользователю данная услуга
     * */
    public Boolean hasService(Integer ownerUserId, Integer serviceId) {
        ru.myrecord.front.data.model.entities.Service service = serviceService.findServiceById(serviceId);
        if ( ownerUserId.equals(service.getUser().getId()) && service.getActive() )
            return true;
        else
            return false;
    }

}
