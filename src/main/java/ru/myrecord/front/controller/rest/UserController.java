package ru.myrecord.front.controller.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.myrecord.front.data.model.Room;
import ru.myrecord.front.data.model.User;
import ru.myrecord.front.service.iface.RoleService;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.ServiceService;
import ru.myrecord.front.service.iface.UserService;

import java.util.List;
import java.util.Set;

/**
 * Created by RuAV on 07.12.2017.
 * Этот класс потом надо разделить на несколько,
 * по логике
 */
@RestController
@RequestMapping("/rest")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ServiceService serviceService;


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

}
