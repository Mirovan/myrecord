package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Controller
public class UserController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private UserProductSalaryService userProductSalaryService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    /**
     * Отображение всех пользователей
     * */
    @RequestMapping(value="/cabinet/users/", method = RequestMethod.GET)
    public ModelAndView showUsers(Principal principal) {
        User user = userService.findUserByEmail( principal.getName() );
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject( "users", userService.findWorkersByOwner(user));
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
     * Форма редактирования системы оклада пользователя
     * */
    @RequestMapping(value="/cabinet/users/{workerId}/salary/", method = RequestMethod.GET)
    public ModelAndView editUserSalaryForm(@PathVariable Integer workerId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, workerId) ) {
            User worker = userService.findUserById(workerId);
            UserSalary userSalary = userSalaryService.findByUser(worker);

            if (userSalary != null) {
                if (userSalary.getSalary() != null && userSalary.getSalary() < 0.001) userSalary.setSalary(0F);
                if (userSalary.getSalaryPercent() != null && userSalary.getSalaryPercent() < 0.001) userSalary.setSalaryPercent(0F);
            } else {
                userSalary = new UserSalary();
                userSalary.setWorker(worker);
            }

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("worker", worker);
            modelAndView.addObject("usersalary", userSalary);
            modelAndView.setViewName("cabinet/user/salary/edit");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Сохранение системы оклада пользователя
     * */
    @RequestMapping(value="/cabinet/users/salary/", method = RequestMethod.POST)
    public ModelAndView editUserSalaryPost(@RequestParam Integer userId,
                                           UserSalary userSalary,
                                           Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) && userSalary.getWorker().getId().equals(userId) ) {
            User user = userService.findUserById(userId);
            userSalary.setStartdate(LocalDateTime.now());
            //ToDo: если процент и оклад остались такими же то просто обновить, а не добавлять заново
            userSalaryService.add(userSalary);

            return new ModelAndView("redirect:/cabinet/users/" + String.valueOf(userId) + "/salary/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Просмотр системы оклада пользователя
     * */
    @RequestMapping(value="/cabinet/users/{userId}/products/salary/", method = RequestMethod.GET)
    public ModelAndView showUserProductSalaries(@PathVariable Integer userId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) ) {
            User user = userService.findUserById(userId);

            //Set<Product> products = new HashSet<>();
            Set<UserProductSalary> userProductSalaries = new HashSet<>();
            //находим все услуги пользователя
            Set<UserProduct> userProducts = userProductService.findByUserActiveLink(user);
            for (UserProduct userProduct: userProducts) {
                Product product = userProduct.getProduct();
                //products.add(product);
                UserProductSalary userProductSalary = userProductSalaryService.findByUserAndProduct(user, product);
                //Если з/п у сотрудника еще не установлена
                if ( userProductSalary == null ) {
                    userProductSalary = new UserProductSalary(user, product, 0F, 0F);
                }
                if (userProductSalary.getSalary() != null && userProductSalary.getSalary() < 0.001) userProductSalary.setSalary(0F);
                if (userProductSalary.getSalaryPercent() != null && userProductSalary.getSalaryPercent() < 0.001) userProductSalary.setSalaryPercent(0F);
                userProductSalaries.add(userProductSalary);
            }

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("user", user);
            modelAndView.addObject("userId", userId);
            modelAndView.addObject("userProductSalaries", userProductSalaries);
            modelAndView.setViewName("cabinet/user/products/salary/index");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Форма редактирования системы оклада пользователя
     * */
    @RequestMapping(value="/cabinet/users/{workerId}/products/{productId}/salary/", method = RequestMethod.GET)
    public ModelAndView editUserProductSalaryForm(@PathVariable Integer workerId, @PathVariable Integer productId, Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, workerId) ) {
            User worker = userService.findUserById(workerId);
            Product product = productService.findProductById(productId);

            UserProductSalary userProductSalary = userProductSalaryService.findByUserAndProduct(worker, product);
            //Если з/п у сотрудника еще не установлена
            if ( userProductSalary == null ) {
                userProductSalary = new UserProductSalary(worker, product, 0F, 0F);
            }
            if (userProductSalary.getSalary() != null && userProductSalary.getSalary() < 0.001) userProductSalary.setSalary(0F);
            if (userProductSalary.getSalaryPercent() != null && userProductSalary.getSalaryPercent() < 0.001) userProductSalary.setSalaryPercent(0F);

            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("worker", worker);
            modelAndView.addObject("workerId", workerId);
            modelAndView.addObject("product", product);
            modelAndView.addObject("userProductSalary", userProductSalary);
            modelAndView.setViewName("cabinet/user/products/salary/edit");
            return modelAndView;
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


    /**
     * Обновление з/п сотрудика за определенный продукт
     * */
    @RequestMapping(value="/cabinet/users/products/salary/", method = RequestMethod.POST)
    public ModelAndView editUserProductSalaryPost(@RequestParam Integer userId,
                                                  @RequestParam Integer productId,
                                                  UserProductSalary userProductSalary,
                                                  Principal principal) {
        //Проверка - имеет ли текущий сис.пользователь доступ к сущности
        if ( userService.hasUser(principal, userId) && userService.hasUser(principal, userId) ) {
            User user = userService.findUserById(userId);
            Product product = productService.findProductById(productId);

            //Обновляем старую запись о з/п
            UserProductSalary oldUserProductSalary = userProductSalaryService.findByUserAndProduct(user, product);
            if (oldUserProductSalary != null) {
                oldUserProductSalary.setEnddate(LocalDateTime.now());
                userProductSalaryService.update(oldUserProductSalary);
            }
            userProductSalary.setProduct(product);
            userProductSalary.setStartdate(LocalDateTime.now());
            userProductSalaryService.add(userProductSalary);

            return new ModelAndView("redirect:/cabinet/users/" + String.valueOf(userId) + "/products/" + String.valueOf(productId) + "/salary/");
        } else {
            return new ModelAndView("redirect:/cabinet/");
        }
    }


}
