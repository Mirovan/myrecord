package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.adapters.WorkerSalary;
import ru.myrecord.front.data.model.entities.*;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
    public ModelAndView showRecords(Principal principal) {
//        @RequestParam(required = false) String from,
//        @RequestParam(required = false) String to,

        //LocalDate from = LocalDate.of(LocalDate.now().getYear(), LocalDate.now().getMonth(), 1);
        LocalDate from = LocalDate.now().withDayOfMonth(1);
        LocalDate to = LocalDate.now().withDayOfMonth(LocalDate.now().lengthOfMonth());

        User ownerUser = userService.findUserByEmail(principal.getName());

        //находим все записи по дате
        List<ClientRecord> clientRecords = clientRecordService.findByDates(from, to, ownerUser);

        //находим все оплаты по записям
        List<ClientPayment> clientPayments = clientPaymentService.findByRecords(clientRecords);

        //Находим всех сотрудников
        Set<User> workers = userService.findWorkersByOwner(ownerUser);

        //находим все оплаты услуг
        List<ClientPaymentProduct> clientPaymentProducts = clientPaymentProductService.findByPaymentsAndWorkers(clientPayments);

        List<WorkerSalary> workerSalaries = new ArrayList<>();
        for (User worker : workers) {
            Integer salaryByRenderProducts = 0;
            Integer monthSalary = 0;
            Integer salaryByPercent = 0;
            Integer salaryByPercentProducts = 0;

            //суммируем доход от оказанных услуг
            for (ClientPaymentProduct item : clientPaymentProducts) {
                if ( item.getWorker() != null && item.getWorker().getId().equals(worker.getId()) ) {
                    salaryByRenderProducts += item.getPrice();

                    //UserProductSalary userProductSalary = userProductSalaryService.findByUserAndProduct(worker, item.getProduct());
                }
            }

            UserSalary userSalary = userSalaryService.findByUser(worker);
            if ( userSalary != null ) {
                monthSalary = userSalary.getSalary().intValue();
                salaryByPercent = ((Float) (salaryByRenderProducts / 100 * userSalary.getSalaryPercent())).intValue();
            }

            WorkerSalary workerSalary = new WorkerSalary(worker, salaryByRenderProducts, monthSalary, salaryByPercent, salaryByPercentProducts);
            workerSalaries.add(workerSalary);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("workerSalaries", workerSalaries);
        modelAndView.addObject("menuSelect", "statistics");
        modelAndView.setViewName("cabinet/statistic/worker/index");
        return modelAndView;
    }


}
