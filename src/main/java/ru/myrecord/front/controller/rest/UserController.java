package ru.myrecord.front.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.Utils.Utils;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.Schedule;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by RuAV on 07.12.2017.
 * Этот класс потом надо разделить на несколько,
 * по логике
 */
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

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

    @RequestMapping(value="/cabinet/users/schedule/", method = RequestMethod.GET)
    public List<List<Schedule>> getMonthSchedule(Integer userId, Integer year, Integer month, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();

        List<List<Schedule>> scheduleAll = new ArrayList<>();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            LocalDate date = LocalDate.of(year, month, 1);   //Дата по году и месяцу

            //Заполняем нулями первые элементы массива, в зависимости каким был первый день месяца
            scheduleAll.add(new ArrayList<>());
            for (int i=1; i<date.withDayOfMonth(1).getDayOfWeek().getValue(); i++) {
                scheduleAll.get(scheduleAll.size()-1).add( new Schedule() );
            }
            //Заполняем двуменрый массив датами
            for (int i=1; i<=date.lengthOfMonth(); i++) {
                //увеличиваем размер массива
                if ( date.withDayOfMonth(i).getDayOfWeek().getValue() == 1 ) {
                    scheduleAll.add(new ArrayList<>());
                }
                //создаем День
                Schedule schedule = new Schedule();
                schedule.setSdate(date.withDayOfMonth(i));
                scheduleAll.get(scheduleAll.size()-1).add( schedule );
            }
        }
        return scheduleAll;
    }


    @RequestMapping(value="/cabinet/users/user-schedule/", method = RequestMethod.GET)
    public List<Schedule> getUserSchedule(Integer userId, Integer year, Integer month, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();

        List<Schedule> scheduleUser = new ArrayList<>();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            scheduleUser = scheduleService.findByUser(user);
        }
        return scheduleUser;
    }

}
