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
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class UserController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserRoomService userRoomService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Отображение всех пользователей
     * */
    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView showUsers(Principal principal) {
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
    public ModelAndView addSimpleUserForm() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("action", "add");

        User user = new User();
        Set<Role> rolesAvailable = userService.getRolesForSimpleUser();
        modelAndView.addObject("roles", rolesAvailable); //Роли из БД
        modelAndView.addObject("user", user);
        modelAndView.setViewName("cabinet/user/edit");
        return modelAndView;
    }


    /**
     * Сохранение добавляемого пользователя
     * */
    @RequestMapping(value="/cabinet/users/add/", method = RequestMethod.POST)
    public ModelAndView addSimpleUserPost(User user, Principal principal) {
        //проверка - можем ли добавить данную роль для своего сотрудника
        if ( userService.hasAccessToRoles(principal, user.getRoles()) ) {  //Проверка удачная - роль существует в списке доступных для этого системного пользователя
            User ownerUser = userService.findUserByEmail(principal.getName());
            user.setOwnerUser(ownerUser);
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
    public ModelAndView updateSimpleUserForm(@PathVariable Integer userId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) ) {
            User user = userService.findUserById(userId);
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("action", "edit");
            if (user.getActive() == true) {
                user.setPass("");
                modelAndView.addObject("user", user);

                //Роли именно этого пользователя
                Set<Role> userRoles = user.getRoles();
                modelAndView.addObject("userRoles", userRoles);
                //Все доступные роли
                Set<Role> rolesAvailable = userService.getRolesForSimpleUser();
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
    public ModelAndView updateSimpleUserPost(User userUpd, Principal principal) {
        //Проверка - исеет ли текущий сис.пользователь доступ к сущности
        // и роль существует в списке доступных для этого системного пользователя
        if ( userService.hasUser(principal, userUpd.getId()) &&
             userService.hasAccessToRoles(principal, userUpd.getRoles()) ) {
            //Находим этого пользователя
            User user = userService.findUserById(userUpd.getId());
            //Обновляем данные
            user.setName(userUpd.getName());
            user.setSirname(userUpd.getSirname());
            user.setRoles(userUpd.getRoles());
            if (userUpd.getPass() != null) {
                user.setPass(bCryptPasswordEncoder.encode(userUpd.getPass()));
            }
            user.setOwnerUser(user.getOwnerUser());
            userService.update(user);
        }
        return new ModelAndView("redirect:/cabinet/users/");
    }


    /**
     * Удаление пользователя
     * */
    @RequestMapping(value="/cabinet/users/delete/{userId}/", method = RequestMethod.GET)
    public ModelAndView deleteRoomPost(@PathVariable Integer userId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) ) {
            User user = userService.findUserById(userId);
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
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasRoom(principal, roomId) ) {
            Room room = roomService.findRoomById(roomId);
            User ownerUser = room.getOwnerUser();
            Set<Product> products = productService.findProductsByRoom(room);
            //Отображаем только нужные данные о пользователях используя Адаптер
            Set<UserAdapter> users = userService.getUserAdapterCollection( userService.findUsersByOwner(ownerUser) );

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("roomId", roomId);
            modelAndView.addObject("users", users);
            modelAndView.addObject("products", products);
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
                                           @RequestParam Integer userId,
                                           @RequestParam(value="products[]", required = false) List<Integer> productsIds,
                                           Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) && userService.hasRoom(principal, roomId) && userService.hasProducts(principal, productsIds) ) {
            //ToDo: Добавляем пользователя в комнату и его услуги в этой комнате
            Room room = roomService.findRoomById(roomId);
            User user = userService.findUserById(userId);
            Set<Product> products = new HashSet<>(); //чтобы исключить дублирование заводим Set
            for (Integer item: productsIds) {
                products.add(productService.findProductById(item));
            }

            Set<User> users = new HashSet<>();
            users.add(user);

            Set<Room> rooms = new HashSet<>();
            rooms.add(room);

            UserRoom userRoom = new UserRoom(user, room);

            //ToDo: протестить добавление
            userRoomService.add(userRoom);

//            userService.update(user);
//            roomService.update(room);
            for (Product product: products) {
                UserProduct userProduct = new UserProduct(user, product);
                userProductService.update(userProduct);
            }

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
    public ModelAndView showSchedule(@PathVariable Integer userId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) ) {
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
    public ModelAndView saveSchedule(@RequestParam Integer userId,
                                     @RequestParam Integer year,
                                     @RequestParam Integer month,
                                     @RequestParam(value="dates[]", required = false) List<String> dates,
                                     Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) ) {
            User user = userService.findUserById(userId);
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
