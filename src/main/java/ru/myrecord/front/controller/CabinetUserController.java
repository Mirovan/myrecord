package ru.myrecord.front.controller;

import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.*;
import ru.myrecord.front.service.iface.*;
import ru.myrecord.front.Utils.Utils;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


@Controller
public class CabinetUserController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView users(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "users", userService.findUsersByOwner(user));
        System.out.println("users = " + userService.findUsersByOwner(user));
        modelAndView.setViewName("cabinet/user/index");
        return modelAndView;
    }


    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.GET)
    public ModelAndView simpleUserAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");

        User user = new User();
        Set<Role> rolesAvailable = getRolesForSysUser();
        modelAndView.addObject("roles", rolesAvailable); //Роли из БД
        modelAndView.addObject("user", user);
        modelAndView.setViewName("cabinet/user/edit");
        return modelAndView;
    }


    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.POST)
    public ModelAndView roomAddPost(User user, Principal principal) {
        User sysUser = userService.findUserByEmail(principal.getName());
        user.setOwnerUser(sysUser);

        //проверка - можем ли добавить данную роль для своего сотрудника
        Set<Role> rolesAvailable = getRolesForSysUser();
        if ( rolesAvailable.containsAll(user.getRoles()) ) {  //Проверка удачная - роль существует в списке доступных для этого системного пользователя
            user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
            user.setActive(true);
            userService.addSimpleUser(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }


    @RequestMapping(value="/cabinet/users/edit/{userId}/", method = RequestMethod.GET)
    public ModelAndView serviceUpdate(@PathVariable Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "edit");
            if (user.getActive() == true) {
                user.setPass("");
                modelAndView.addObject("user", user);

                //Роли именно этого пользователя
                Set<Role> userRoles = user.getRoles();
                modelAndView.addObject("userRoles", userRoles);
                //Все доступные роли
                Set<Role> rolesAvailable = getRolesForSysUser();
                modelAndView.addObject("roles", rolesAvailable);

                modelAndView.setViewName("cabinet/user/edit");
            } else {
                return new ModelAndView("redirect:/cabinet/users/");
            }
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/users/");
        }
    }


    @RequestMapping(value="/cabinet/users/edit/", method = RequestMethod.POST)
    public ModelAndView roomEditPost(User userUpd, Principal principal) {
        //Находим этого пользователя
        User user = userService.findUserById(userUpd.getId());
        User ownerUser = user.getOwnerUser();
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            //Обновляем данные
            user.setName(userUpd.getName());
            user.setSirname(userUpd.getSirname());
            user.setRoles(userUpd.getRoles());
            if (userUpd.getPass() != null) {
                user.setPass(bCryptPasswordEncoder.encode(userUpd.getPass()));
            }
            user.setOwnerUser(ownerUser);
            userService.update(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }


    @RequestMapping(value="/cabinet/users/delete/{userId}/", method = RequestMethod.GET)
    public ModelAndView roomPost(@PathVariable Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            user.setActive(false);
            userService.update(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }


    public Set<Role> getRolesForSysUser() {
        List<String> roleNames = new ArrayList<String>();
        roleNames.add("MASTER");
        roleNames.add("MANAGER");
        Set<Role> roles = roleService.findRolesByRoleName( roleNames );
        return roles;
    }


    @RequestMapping(value="/cabinet/rooms/addusers/{roomId}/", method = RequestMethod.GET)
    public ModelAndView addUserToRoom(@PathVariable Integer roomId, Principal principal) {
        Room room = roomService.findRoomById(roomId);
        User user = room.getUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), user.getId()) &&
                room.getActive() == true ) { //активна ли сущность
            //Set<Room> rooms = roomService.findRoomsByActive(user);
            // TODO: 05.12.2017 - add services to user
            Set<Service> services = serviceService.findServicesByRoom(room);
            //Set<User> users = userService.findUsersByRoom(room);
            Set<User> users = userService.findUsersByOwner(user);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("users", users);
            //modelAndView.addObject("service", services);
            modelAndView.setViewName("cabinet/room/adduser");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/services/");
        }
    }


    @RequestMapping(value="/cabinet/users/{userId}/schedule/", method = RequestMethod.GET)
    public ModelAndView scheduleView(@PathVariable Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            ModelAndView modelAndView = new ModelAndView();
            LocalDate date = LocalDate.now();
            modelAndView.addObject("year", date.getYear());
            modelAndView.addObject("month", date.getMonthValue());
            modelAndView.addObject("userId", userId);
            modelAndView.setViewName("cabinet/user/schedule/index");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/users/");
        }
    }


    @RequestMapping(value="/cabinet/users/saveschedule/", method = RequestMethod.POST)
    public ModelAndView scheduleSave(@RequestParam Integer userId,
                                     @RequestParam Integer year,
                                     @RequestParam Integer month,
                                     @RequestParam(value="dates[]", required = false) List<String> dates,
                                     Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( Utils.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            LocalDate localDate = LocalDate.of(year, month, 1); //текущая дата полученого года и месяца
            int lastMonthDay = localDate.lengthOfMonth();   //последний день месяца

            //Перебираем все дни полученного месяца
            for (int i=1; i<=lastMonthDay; i++) {
                LocalDate date = LocalDate.of(year, month, i);  //создаем i-й день
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                String formattedDate = date.format(formatter);  //получаем строковое значение q-й даты

                //Если i-я дата пришла в списке - пытаемся её добавить в БД
                if ( dates.contains(formattedDate) ) {
                    //Создем объект - день расписания
                    Schedule schedule = new Schedule();
                    schedule.setUser(user);
                    formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    date = LocalDate.parse(dates.get(i), formatter);
                    schedule.setSdate(date);

                    //Защита - чтобы левые данные не добавляли, а только этого месяца
                    if ( date.getMonthValue() == month && date.getYear() == year ) {
                        //Определяем есть ли такая запись уже в БД
                        Schedule existSchedule = scheduleService.findByUserAndSdate(user, date);
                        if ( existSchedule == null ) {  //Такой записи нет - добавляем
                            scheduleService.add(schedule);
                        }
                    }
                } else {    //Пытаемся удалить i-ю дату из БД
                    //ToDo: Доделать удаление
                    scheduleService.removeScheduleByDate(user, date);
                }
            }

            /*
            for (int i=0; i<dates.size(); i++) {
                Schedule schedule = new Schedule();
                schedule.setUser(user);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                LocalDate date = LocalDate.parse(dates.get(i), formatter);
                schedule.setSdate(date);
                //Защита - чтобы левые данные не добавляли, а только этого месяца
                if ( date.getMonthValue() == month && date.getYear() == year ) {
                    //Определяем есть ли такая запись уже в БД
                    Schedule existSchedule = scheduleService.findByUserAndSdate(user, date);
                    if ( existSchedule == null ) {  //Такой записи нет - добавляем
                        scheduleService.add(schedule);
                    }
                }
            }
            */
            return new ModelAndView("redirect:/cabinet/users/" + String.valueOf(userId) + "/schedule/");
        } else {
            return new ModelAndView("redirect:/cabinet/users/");
        }
    }
}
