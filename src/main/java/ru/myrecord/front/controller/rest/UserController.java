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


    private class UserMonthSchedule {
        private List<Schedule> userSchedule;
        private List<List<Schedule>> scheduleAll;

        public List<Schedule> getUserSchedule() {
            return userSchedule;
        }

        public void setUserSchedule(List<Schedule> userSchedule) {
            this.userSchedule = userSchedule;
        }

        public List<List<Schedule>> getScheduleAll() {
            return scheduleAll;
        }

        public void setScheduleAll(List<List<Schedule>> scheduleAll) {
            this.scheduleAll = scheduleAll;
        }
    }

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

    @RequestMapping(value="/cabinet/users/month-schedule/", method = RequestMethod.GET)
    public UserMonthSchedule getScheduleData(Integer userId, Integer year, Integer month, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();

        UserMonthSchedule userMonthSchedule = new UserMonthSchedule();

        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            userMonthSchedule.setScheduleAll( scheduleService.getMonthSchedule(year, month) );  //Получаем список - месячный календарь
            userMonthSchedule.setUserSchedule( scheduleService.findByUser(user) );  //Получаем расписание пользователя
        }
        return userMonthSchedule;
    }

}
