package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.enums.UserRoles;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.ScheduleService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

/**
 * Created by RuAV on 07.12.2017.
 * Этот класс потом надо разделить на несколько,
 * по логике
 */
@RestController
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ProductService productService;

/*
    @RequestMapping(value="/getUsersByRoom/{roomId}", method = RequestMethod.GET)
    public Set<User> getUsersByRoom(@PathVariable Integer roomId) {
//        System.out.println("roomId = " + roomId);
//        System.out.println("roomById = " + roomService.findRoomById(roomId).toString());
//        System.out.println("user = " + userService.findUsersByRoom(roomService.findRoomById(roomId)).toString());
        return userService.findUsersByRoom(roomService.findRoomById(roomId));
    }


    @RequestMapping(value="/getRoom/{roomId}", method = RequestMethod.GET)
    public Room getRoom(@PathVariable Integer roomId) {
        return roomService.findRoomById(roomId);
    }


    @RequestMapping(value="/getRooms", method = RequestMethod.GET)
    public List<Room> getRooms() {
//        System.out.println("rooms " + roomService.findAll().toString());
        return roomService.findAll();
    }
*/

    /**
     * Запрос расписания
     * */
    @RequestMapping(value="/cabinet/users/json-month-schedule/", method = RequestMethod.GET)
    public List<CalendarAdapter> getCalendar(Integer workerId, Integer year, Integer month, Principal principal) {
        User user = userService.findUserById(workerId);
        User ownerUser = user.getOwnerUser();

        List<CalendarAdapter> calendar = scheduleService.getMonthCalendar(year, month, ownerUser, user);

        //ScheduleAdapter userMonthSchedule = new ScheduleAdapter();

        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            //userMonthSchedule.setScheduleAll( scheduleService.getMonthCalendar(year, month) );  //Получаем список - месячный календарь
            //userMonthSchedule.setUserSchedule( scheduleService.findByWorker(user) );  //Получаем расписание пользователя
        //}
        return calendar;
    }


    /**
     * Запрос работников для текущего сис.пользователя
     * */
    @RequestMapping(value="/cabinet/users/json-users/", method = RequestMethod.GET)
    public Set<User> getUsers(Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();

        Set<User> users = null;
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            users = userService.findWorkersByOwner(user);
        //}
        return users;
    }


    @RequestMapping(value="/cabinet/users/json-sysusers/", method = RequestMethod.GET)
    public Set<User> getSysUsers(Principal principal) {


        Set<User> users = userService.findByRole(UserRoles.SYSUSER);
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
//        users = userService.findWorkersByOwner(user);
        //}
        return users;
    }

    @RequestMapping(value="/cabinet/users/json-sysusers/{role}", method = RequestMethod.GET)
    public Set<User> getUsersByRole(Principal principal, @PathVariable String role) {


        Set<User> users = userService.findByRole(UserRoles.valueOf(role.toUpperCase()));
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
//        users = userService.findWorkersByOwner(user);
        //}
        return users;
    }

    @RequestMapping(value="/cabinet/users/json-payments/{id}", method = RequestMethod.GET)
    public Set<Payment> getPaymentsById(Principal principal, @PathVariable int id) {


        Set<Payment> payments = null;


        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
//        users = userService.findWorkersByOwner(user);
        //}
        return payments;
    }



    /**
     * Запрос услуг для кабинета пользователя
     * */
//    @RequestMapping(value="/cabinet/rooms/user/json-services/", method = RequestMethod.GET)
//    public Set<Product> getRoomServices(Integer roomId, Principal principal) {
//        Room room = roomService.findRoomById(roomId);
//        User ownerUser = room.getUser();
//
//        Set<Product> res = null;
//
//        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
//        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
//            res = productService.findProductsByRoom(room);  //Получаем все услуги для текущей комнаты
//        }
//        return res;
//    }

}
