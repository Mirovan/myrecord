package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.RoomService;
import ru.myrecord.front.service.iface.ScheduleService;
import ru.myrecord.front.service.iface.ServiceService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;


@Controller
public class UserController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ServiceService serviceService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Отображение всех пользователей
     * */
    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView users(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "users", userService.findUsersByOwner(user));
        System.out.println("users = " + userService.findUsersByOwner(user));
        modelAndView.setViewName("cabinet/user/index");
        return modelAndView;
    }


    /**
     * Форма добавления пользователя
     * */
    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.GET)
    public ModelAndView simpleUserAdd() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");

        User user = new User();
        Set<Role> rolesAvailable = userService.getRolesForSysUser();
        modelAndView.addObject("roles", rolesAvailable); //Роли из БД
        modelAndView.addObject("user", user);
        modelAndView.setViewName("cabinet/user/edit");
        return modelAndView;
    }


    /**
     * Сохранение добавляемого пользователя
     * */
    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.POST)
    public ModelAndView simpleUserAddPost(User user, Principal principal) {
        User sysUser = userService.findUserByEmail(principal.getName());
        user.setOwnerUser(sysUser);

        //проверка - можем ли добавить данную роль для своего сотрудника
        Set<Role> rolesAvailable = userService.getRolesForSysUser();
        if ( rolesAvailable.containsAll(user.getRoles()) ) {  //Проверка удачная - роль существует в списке доступных для этого системного пользователя
            user.setPass(bCryptPasswordEncoder.encode(user.getPass()));
            user.setActive(true);
            userService.addSimpleUser(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }


    /**
     * Форма редактирования пользователя
     * */
    @RequestMapping(value="/cabinet/users/edit/{userId}/", method = RequestMethod.GET)
    public ModelAndView serviceUpdate(@PathVariable Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "edit");
            if (user.getActive() == true) {
                user.setPass("");
                modelAndView.addObject("user", user);

                //Роли именно этого пользователя
                Set<Role> userRoles = user.getRoles();
                modelAndView.addObject("userRoles", userRoles);
                //Все доступные роли
                Set<Role> rolesAvailable = userService.getRolesForSysUser();
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


    /**
     * Сохранение редактируемого пользователя
     * */
    @RequestMapping(value="/cabinet/users/edit/", method = RequestMethod.POST)
    public ModelAndView roomEditPost(User userUpd, Principal principal) {
        //Находим этого пользователя
        User user = userService.findUserById(userUpd.getId());
        User ownerUser = user.getOwnerUser();
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
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


    /**
     * Удаление пользователя
     * */
    @RequestMapping(value="/cabinet/users/delete/{userId}/", method = RequestMethod.GET)
    public ModelAndView roomPost(@PathVariable Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            user.setActive(false);
            userService.update(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }


    /**
     * Форма добавления пользователя в помещение
     * */
    @RequestMapping(value="/cabinet/rooms/{roomId}/addusers/", method = RequestMethod.GET)
    public ModelAndView addUsersToRoom(@PathVariable Integer roomId, Principal principal) {
        Room room = roomService.findRoomById(roomId);
        User ownerUser = room.getUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) &&
                room.getActive() == true ) { //активна ли сущность
            Set<Service> services = serviceService.findServicesByRoom(room);
            //Отображаем только нужные данные о пользователях используя Адаптер
            Set<UserAdapter> users = userService.getUserAdapterCollection( userService.findUsersByOwner(ownerUser) );

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("roomId", roomId);
            modelAndView.addObject("users", users);
            modelAndView.addObject("services", services);
            modelAndView.setViewName("cabinet/room/adduser");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Добавление пользователя в помещение и его услуг
     * */
    @RequestMapping(value="/cabinet/rooms/users/add/", method = RequestMethod.POST)
    public ModelAndView addUsersToRoomPost(@RequestParam Integer roomId,
                                           @RequestParam Integer addingUserId,
                                           @RequestParam(value="services[]", required = false) List<String> services,
                                           Principal principal) {
        Room room = roomService.findRoomById(roomId);
        User addingUser = userService.findUserById(addingUserId);
        User ownerUser = room.getUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        //if (userService.hasUser(principal.getName()).getId()))
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) &&
                (room.getActive() == true)
                ) {
            //ToDo: Добавляем пользователя в комнату и его услуги в этой комнате
            //UserRoom userRoom = new UserRoom();

            return new ModelAndView("redirect:/cabinet/rooms/users/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Форма отображения пользователей в помещении
     * */
    @RequestMapping(value="/cabinet/rooms/users/", method = RequestMethod.GET)
    public ModelAndView viewRoomUsers(Principal principal) {
        //ToDo: отображаем пользователей в этой комнате
        return new ModelAndView("cabinet/room/users/");
    }


    /**
     * Форма отображение расписания пользователя
     * */
    @RequestMapping(value="/cabinet/users/{userId}/schedule/", method = RequestMethod.GET)
    public ModelAndView scheduleView(@PathVariable Integer userId, Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
            ModelAndView modelAndView = new ModelAndView();
            LocalDate date = LocalDate.now();
            modelAndView.addObject("year", date.getYear());
            modelAndView.addObject("month", date.getMonthValue());
            modelAndView.addObject("userId", userId);
            modelAndView.setViewName("cabinet/user/schedule/index");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Сохранение/Изменение расписания пользователя
     * */
    @RequestMapping(value="/cabinet/users/saveschedule/", method = RequestMethod.POST)
    public ModelAndView scheduleSave(@RequestParam Integer userId,
                                     @RequestParam Integer year,
                                     @RequestParam Integer month,
                                     @RequestParam(value="dates[]", required = false) List<String> dates,
                                     Principal principal) {
        User user = userService.findUserById(userId);
        User ownerUser = user.getOwnerUser();
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.userEquals(userService.findUserByEmail(principal.getName()).getId(), ownerUser.getId()) ) {
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
                    scheduleService.removeScheduleByDate(user, date);
                }
            }

            return new ModelAndView("redirect:/cabinet/users/" + String.valueOf(userId) + "/schedule/");
        } else {
            return new ModelAndView("redirect:/cabinet/users/");
        }
    }

}
