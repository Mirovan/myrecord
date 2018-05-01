package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ScheduleService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Controller
public class ScheduleController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ScheduleService scheduleService;

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
            User worker = userService.findUserById(userId);
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
                    schedule.setWorker(worker);
                    schedule.setSdate(date);

                    //Защита - чтобы левые данные не добавляли, а только этого месяца
                    if ( date.getMonthValue() == month && date.getYear() == year ) {
                        //Определяем есть ли такая запись уже в БД
                        Schedule existSchedule = scheduleService.findByUserAndDate(worker, date);
                        if ( existSchedule == null ) {  //Такой записи нет - добавляем
                            scheduleService.add(schedule);
                        }
                    }
                } else {    //Пытаемся удалить i-ю дату из БД
                    scheduleService.removeScheduleByDate(worker, date);
                }
            }

            return new ModelAndView("redirect:/cabinet/users/" + String.valueOf(userId) + "/schedule/");
        } else {
            return new ModelAndView("redirect:/cabinet/users/");
        }
    }

}
