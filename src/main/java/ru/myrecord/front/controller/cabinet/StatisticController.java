package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.MoneyStatistic;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class StatisticController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private ClientPaymentService clientPaymentService;

    @Autowired
    private ClientPaymentProductService clientPaymentProductService;

    @Autowired
    private UserSalaryService userSalaryService;

    @Autowired
    private UserProductSalaryService userProductSalaryService;

    /**
     * Страница отображения всех пользователей и их з/п за период
     * */
    @RequestMapping(value="/cabinet/statistics/workers/", method = RequestMethod.GET)
    public ModelAndView showPeriodRecords(Principal principal,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fromDate,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate toDate) {

        //Если даты не выбирали
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (fromDate == null) fromDate = LocalDate.now().withDayOfMonth(1);
        if (toDate == null) toDate = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        User ownerUser = userService.findUserByEmail(principal.getName());

        //находим все записи по дате
        List<ClientRecord> clientRecords = clientRecordService.findByDates(fromDate, toDate, ownerUser);

        //находим все оплаты по записям
        List<ClientPayment> clientPayments = clientPaymentService.findByRecords(clientRecords);

        //Находим всех сотрудников
        Set<User> workers = userService.findWorkersByOwner(ownerUser);

        //находим все оплаты услуг
        List<ClientPaymentProduct> clientPaymentProducts = clientPaymentProductService.findByPaymentsAndWorkers(clientPayments);

        //результат ИТОГО
        MoneyStatistic resPeriodMoneyStatistic = new MoneyStatistic(null, 0, 0, 0, 0, 0);

        List<MoneyStatistic> moneyStatisticList = new ArrayList<>();
        for (User worker : workers) {
            Integer earnedMoney = 0;    //Доход
            Integer primeCostSum = 0;   //Себестоимость
            Integer allNetProfit = 0;  //Вся чистая прибыль
            Integer workerPeriodSalary = 0;  //Фиксированная з/п сотрудника
            Integer workerInterestSalary = 0;    //З/п от прибыли
            Integer workerInterestProductSalary = 0;    //З/п от каждой услуги  //ToDo: реализовать з/п сотрудника как процент от каждой услуги

            //Выбираем только услуги оказанные нужным сотрудником
            Set<ClientPaymentProduct> clientPaymentProductsByWorker = clientPaymentProducts
                    .stream()
                    .filter(f -> f.getWorker().equals(worker))
                    .collect(Collectors.toSet());
            //Находим сумму по полученным деньгам за оказанные услуги
            for (ClientPaymentProduct item : clientPaymentProductsByWorker) {
                earnedMoney += item.getPrice();                         //суммируем доход от оказанных услуг
                primeCostSum += item.getProduct().getPrimeCost();       //суммируем себестоимость оказанных услуг
            }

            //Вся Чистая прибыль
            allNetProfit = earnedMoney - primeCostSum;

            //З/п сотрудника фиксированная и общий процент
            UserSalary userSalary = userSalaryService.findByUser(worker);
            if ( userSalary != null ) {
                workerPeriodSalary += userSalary.getSalary().intValue();      //Фиксированная составляющая з/п сотрудника
                workerInterestSalary += ((Float) ((float) allNetProfit / 100 * userSalary.getSalaryPercent())).intValue();     //З/п сотрудника - Процент от прибыли
            }


            //Услуги, оказанные сотрудником
            Set<Product> renderedWorkerProducts = clientPaymentProductsByWorker
                    .stream()
                    .map(ClientPaymentProduct::getProduct)
                    .collect(Collectors.toSet());


            //Находим суммарную выручку за определенную услугу
            Map<Integer, Integer> productProfitMap = new HashMap();    //<Id услуги, сумма выручки>
            //Просматриваем все оплаченные услуги и добавляем в Map пары - <Id услуги, сумма>
            for (ClientPaymentProduct item: clientPaymentProductsByWorker) {
                if (!productProfitMap.containsKey(item.getProduct().getId()))  //Добавляем в map
                    productProfitMap.put(item.getProduct().getId(), item.getPrice());
                else    //обновляем, прибавляя оплату
                    productProfitMap.put(
                            item.getProduct().getId(), productProfitMap.get(item.getProduct().getId()) + item.getPrice()
                    );
            }
            //З/п сотрудника по услугам - фиксированная и общий процент
            for (Product product : renderedWorkerProducts) {
                UserProductSalary userProductSalary = userProductSalaryService.findByWorkerAndProduct(worker, product);
                if (userProductSalary != null) {
                    workerPeriodSalary += userProductSalary.getSalary().intValue();      //Фиксированная составляющая з/п сотрудника

                    //Вычитаем себестоимость из выручки за определенную услугу
                    int productProfit = productProfitMap.get(product.getId()) - product.getPrimeCost();

                    workerInterestProductSalary += ((Float) ((float) productProfit / 100 * userProductSalary.getSalaryPercent())).intValue();     //З/п сотрудника - Процент от прибыли
                }
            }


            MoneyStatistic moneyStatistic = new MoneyStatistic(worker, earnedMoney, primeCostSum, workerPeriodSalary, workerInterestSalary, workerInterestProductSalary);
            moneyStatisticList.add(moneyStatistic);

            //добавляем в ИТОГО
            resPeriodMoneyStatistic.plus(earnedMoney, primeCostSum, workerPeriodSalary, workerInterestSalary, workerInterestProductSalary);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("resPeriodMoneyStatistic", resPeriodMoneyStatistic);
        modelAndView.addObject("moneyStatisticList", moneyStatisticList);
        modelAndView.addObject("fromDate", fromDate.format(timeFormatter));
        modelAndView.addObject("toDate", toDate.format(timeFormatter));
        modelAndView.addObject("menuSelect", "statistics");
        modelAndView.setViewName("cabinet/statistic/worker/index");
        return modelAndView;
    }


}
