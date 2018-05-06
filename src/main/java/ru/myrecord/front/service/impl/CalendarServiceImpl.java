package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.model.adapters.CalendarAdapter;
import ru.myrecord.front.service.iface.CalendarService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service("calendarService")
public class CalendarServiceImpl implements CalendarService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    /**
     * Получение календаря (всеъ дней) по месяцу и году
     * */
    @Override
    public List<CalendarAdapter> getMonthCalendar(Integer year, Integer month) {
        List<CalendarAdapter> calendar = new ArrayList<>();
        LocalDate date = LocalDate.of(year, month, 1);   //Дата по году и месяцу

        //Заполняем нулями первые элементы массива, в зависимости каким был первый день месяца
        for (int i=1; i<date.withDayOfMonth(1).getDayOfWeek().getValue(); i++) {
            calendar.add( null );
        }

        //Заполняем массив
        for (int i=1; i<=date.lengthOfMonth(); i++) {
            //создаем День
            LocalDate localDate = date.withDayOfMonth(i);

            CalendarAdapter calendarAdapter = new CalendarAdapter();
            calendarAdapter.setDate(localDate);
            calendar.add(calendarAdapter);

        }
        return calendar;
    }

}
