package ru.myrecord.front.controller.cabinet.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.data.model.adapters.CalendarRecord;
import ru.myrecord.front.data.model.adapters.CalendarWorker;
import ru.myrecord.front.data.model.adapters.UserAdapter;
import ru.myrecord.front.data.model.entities.*;
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
    private ConfigService configService;

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
    @RequestMapping(value="/cabinet/clients/json-month-records/", method = RequestMethod.GET)
    public Set<CalendarRecord> getCalendar(@RequestParam(required = false) Integer year,
                                           @RequestParam(required = false) Integer month,
                                           @RequestParam(required = false) Integer productId,
                                           @RequestParam(required = false) Integer workerId,
                                           Principal principal) {
        User ownerUser = userService.findUserByEmail( principal.getName() );
        if (year == null) year = LocalDate.now().getYear();
        if (month == null) month = LocalDate.now().getMonthValue();

        //Период показа календаря
        LocalDate from = LocalDate.of(year, month, 1);
        LocalDate to = from.plusMonths(1).minusDays(1);

        //Получаем все записи по диапазону дат
        List<ClientRecord> records = clientRecordService.findByDates(from, to, ownerUser);

        //Массив с числом записей в i-м дне месяца
        int[] recordsCount = new int[31];

        Set<CalendarRecord> calendarSet = new HashSet<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        //Находим число записей в каждом дне
        for (ClientRecord item : records) {
            int dayOfMonth = item.getDate().getDayOfMonth();
            recordsCount[dayOfMonth] += 1;
        }

        for (int i=0; i<recordsCount.length; i++) {
            if ( recordsCount[i] != 0 ) {
                LocalDate date = LocalDate.of(year, month, i);
                CalendarRecord calendarRecord = new CalendarRecord(
                        "bg" + String.valueOf(i),
                        recordsCount[i] + " записей",
                        date.format(formatter),
                        date.format(formatter)
                );
                calendarSet.add(calendarRecord);
            }
        }

        return calendarSet;
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
                    String.valueOf(item.getId()),
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

        Config config = configService.findByOwnerUser(ownerUser);

        Set<CalendarWorker> calendarWorkers = new HashSet<>();

        //без учета расписания сотрудников
        if (config.getIsSetSchedule() == false) {
            //Находим всех мастеров без учета расписания
            Set<User> workers = userService.findWorkersByOwner(ownerUser);
            for (User worker: workers) {
                calendarWorkers.add(new CalendarWorker(worker.getId(), worker.getName()));
            }
        } else {
            //Находим всех мастеров кто работает в этот день
            LocalDate date = LocalDate.of(year, month, day);

            Set<Schedule> schedules = scheduleService.findByDate(date, ownerUser);

            for (Schedule item : schedules) {
                String name = item.getWorker().getName() +  " " + item.getWorker().getSirname();

                CalendarWorker calendarRecord = new CalendarWorker(item.getWorker().getId(), name);
                calendarWorkers.add(calendarRecord);
            }
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

        //ToDo: переделать сущность CalendarAdapter
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
                            String.valueOf(dayOfMonth),
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
