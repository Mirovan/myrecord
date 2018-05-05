package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.Schedule;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.adapters.CalendarRecord;
import ru.myrecord.front.data.model.adapters.CalendarWorker;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class ClientRecordRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private ClientRecordProductService clientRecordProductService;

    @Autowired
    private ScheduleService scheduleService;


    /**
     * Запрос месяца
     * */
    @RequestMapping(value="/cabinet/clients/json-calendar/", method = RequestMethod.GET)
    public List<CalendarAdapter> getCalendar(@RequestParam(required = false) Integer year,
                                             @RequestParam(required = false) Integer month,
                                             @RequestParam(required = false) Integer productId,
                                             @RequestParam(required = false) Integer workerId,
                                             Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();
        List<CalendarAdapter> calendar = null;
        if (productId != null && workerId != null) {
            User worker = userService.findUserById(workerId);
            calendar = clientRecordService.getMonthCalendar(year, month, worker, ownerUser);
        } else if (productId != null) {
            Product product = productService.findProductById(productId);
            calendar = clientRecordService.getMonthCalendar(year, month, product, ownerUser);
        } else if (workerId != null) {
            User worker = userService.findUserById(workerId);
            calendar = clientRecordService.getMonthCalendar(year, month, worker, ownerUser);
        } else {
            calendar = clientRecordService.getMonthCalendar(year, month, ownerUser);
        }
        return calendar;
    }


    /**
     * Отображаем мастеров по выбранному продукту
     * */
    @RequestMapping(value="/cabinet/clients/json-users-by-product/", method = RequestMethod.GET)
    public Set<UserAdapter> getMastersByProduct(@RequestParam(required = false) Integer productId, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<User> users = null;
        if (productId != null) {
            Product product = productService.findProductById(productId);
            users = userService.findWorkersByProduct(product);
        } else {
            users = userService.findWorkersByOwner(ownerUser);
        }
        Set<UserAdapter> usersAdapter = userService.getUserAdapterCollection(users);
        return usersAdapter;
    }


    /**
     * Отображаем записи по выбранной дате
     * */
    @RequestMapping(value="/cabinet/clients/json-records-by-date/", method = RequestMethod.GET)
    public Set<CalendarRecord> getRecordsByDate(Integer day,
                                                Integer month,
                                                Integer year,
                                                Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<CalendarRecord> calendarMap = new HashSet<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDate date = LocalDate.of(year, month, day);

        Set<ClientRecordProduct> clientRecords = clientRecordProductService.findByDate(date);

        for (ClientRecordProduct item : clientRecords) {
            LocalDateTime start = item.getSdate();
            LocalDateTime end = item.getSdate().plusHours(2);   //ToDo: сделать наминальное время оказания услуги

            String name = item.getClientRecord().getUser().getName() +  " " + item.getClientRecord().getUser().getSirname();
            UserAdapter master = userService.getUserAdapter(item.getWorker());

            CalendarRecord calendarRecord = new CalendarRecord(
                    item.getId(),
                    name,
                    start.format(formatter),
                    end.format(formatter)
            );
            calendarRecord.setResourceId( String.valueOf(master.getId()) );
            calendarMap.add(calendarRecord);
        }

        return calendarMap;
    }


    /**
     * Отображаем мастеров по выбранной дате
     * */
    @RequestMapping(value="/cabinet/clients/json-workers-by-date/", method = RequestMethod.GET)
    public Set<CalendarWorker> getWorkersByDate(Integer day,
                                                Integer month,
                                                Integer year,
                                                Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        Set<CalendarWorker> calendarWorkers = new HashSet<>();

        LocalDate date = LocalDate.of(year, month, day);

        Set<Schedule> schedules = scheduleService.findByDate(date, ownerUser);

        for (Schedule item : schedules) {
            String name = item.getWorker().getName() +  " " + item.getWorker().getSirname();

            CalendarWorker calendarRecord = new CalendarWorker(item.getWorker().getId(), name);
            calendarWorkers.add(calendarRecord);
        }

        return calendarWorkers;
    }


    /**
     * Отображаем рабочие дни мастеров
     * */
    @RequestMapping(value="/cabinet/clients/json-worker-month-schedule/", method = RequestMethod.GET)
    public Set<CalendarRecord> getWorkersSchedule(Integer month,
                                                  Integer year,
                                                  @RequestParam(required = false) Integer productId,
                                                  @RequestParam(required = false) Integer workerId,
                                                  Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());

        //if (day == null) day = LocalDate.now().getDayOfMonth();
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();

        //Находим все дни когда сотрудники работают в конкретном месяце конкретного года
        List<CalendarAdapter> calendar = clientRecordService.getMonthCalendar(year, month, ownerUser);

        Set<CalendarRecord> calendarMap = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (CalendarAdapter item: calendar) {
            if ( item != null && item.getData() != null ) {
                Set<UserAdapter> workers = (Set<UserAdapter>) item.getData();
                if (workers.size() > 0) {
                    int dayOfMonth = item.getDate().getDayOfMonth();
                    CalendarRecord calendarRecord = new CalendarRecord(
                            dayOfMonth,
                            "",
                            item.getDate().format(formatter),
                            item.getDate().format(formatter)
                    );
                    calendarRecord.setOverlap(true);
                    calendarRecord.setRendering("background");
                    calendarRecord.setColor("#11ff11");
                    calendarMap.add(calendarRecord);
                }
            }
        }

        return calendarMap;
    }

}
