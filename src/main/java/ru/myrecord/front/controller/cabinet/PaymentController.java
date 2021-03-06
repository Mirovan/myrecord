package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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
import java.util.Date;
import java.util.List;
import java.util.Set;

@Controller
public class PaymentController/* implements ErrorController*/{

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductService userProductService;

    @Autowired
    private ClientRecordService clientRecordService;

    @Autowired
    private ClientPaymentService clientPaymentService;

    @Autowired
    private ClientPaymentProductService clientPaymentProductService;


    /**
     * Страница всех записей с отображением кнопки для оплаты
     * */
    @RequestMapping(value="/cabinet/clients/payment/daily/", method = RequestMethod.GET)
    public ModelAndView showDailyRecords(Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());

        LocalDate date = LocalDate.now();
        List<ClientRecord> clientRecords = clientRecordService.findByDate(date, ownerUser);

        for (ClientRecord item : clientRecords) {
            User user = new User(item.getUser().getId(), item.getUser().getName(), item.getUser().getSirname());
            item.setUser(user);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", clientRecords);
        modelAndView.addObject("pageSelect", "daily");
        modelAndView.addObject("menuSelect", "payment");
        modelAndView.setViewName("cabinet/client/payment/index");
        return modelAndView;
    }

    /**
     * Страница всех записей с отображением кнопки для оплаты
     * */
    @RequestMapping(value = "/cabinet/clients/payment/period/", method = RequestMethod.GET)
    public ModelAndView showPeriodRecords(Principal principal,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate fromDate,
                                          @RequestParam(required = false) @DateTimeFormat(pattern="dd-MM-yyyy") LocalDate toDate) {
        User ownerUser = userService.findUserByEmail(principal.getName());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        if (fromDate == null) fromDate = LocalDate.now();
        if (toDate == null) toDate = LocalDate.now();

        List<ClientRecord> clientRecords = clientRecordService.findByDates(fromDate, toDate, ownerUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", clientRecords);
        modelAndView.addObject("fromDate", fromDate.format(timeFormatter));
        modelAndView.addObject("toDate", toDate.format(timeFormatter));
        modelAndView.addObject("pageSelect", "period");
        modelAndView.addObject("menuSelect", "payment");
        modelAndView.setViewName("cabinet/client/payment/index");
        return modelAndView;
    }


    /**
     * Страница оплаты для конкретной записи клиента
     * */
    @RequestMapping(value="/cabinet/clients/payment/record/{recordId}/pay/", method = RequestMethod.GET)
    public ModelAndView showPaymentForm(@PathVariable Integer recordId, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        ClientRecord clientRecord = clientRecordService.findById(recordId);

        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);
        Set<User> workers = userService.findWorkersByOwner(ownerUser);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record", clientRecord);
        modelAndView.addObject("products", products);
        modelAndView.addObject("workers", workers);
        modelAndView.addObject("menuSelect", "payment");
        modelAndView.setViewName("cabinet/client/payment/record");
        return modelAndView;
    }


    /**
     * Сохраняем оплату
     * */
    @RequestMapping(value="/cabinet/clients/payment/pay/", method = RequestMethod.POST)
    public ModelAndView showPaymentFormPost(@RequestParam(required = false) Integer recordId,
                                            @RequestParam(value="products[]", required = false) List<Integer> products,
                                            @RequestParam(value="prices[]", required = false) List<Integer> prices,
                                            @RequestParam(value="workers[]", required = false) List<Integer> workers,
                                            Principal principal) {
        //ToDo: сделать проверку на принадлежность продукта сист пользователю

        ClientRecord clientRecord = clientRecordService.findById(recordId);

        ClientPayment clientPayment = new ClientPayment(clientRecord, false, true);
        clientPayment = clientPaymentService.add(clientPayment);    //получаем сохранённый объект с Id

        for (int i=0; i<products.size(); i++) {
            if (prices.get(i) != null) {
                Product product = productService.findProductById(products.get(i));
                User worker = userService.findUserById(workers.get(i));
                ClientPaymentProduct clientRecordProduct = new ClientPaymentProduct(clientPayment, product, prices.get(i), worker);
                clientPaymentProductService.add(clientRecordProduct);
            }
        }

        clientPayment.setPaid(true);
        clientPaymentService.update(clientPayment);

        return( new ModelAndView("redirect:/cabinet/clients/payment/daily/") );
    }


    /**
     * Страница оказанных услуг
     * */
    @RequestMapping(value="/cabinet/clients/payment/{paymentId}/products/", method = RequestMethod.GET)
    public ModelAndView showPaymentProducts(@PathVariable Integer paymentId, Principal principal) {
        User ownerUser = userService.findUserByEmail(principal.getName());
        ClientPayment clientPayment = clientPaymentService.findById(paymentId);
        LocalDate date = clientPayment.getClientRecord().getDate();
        User client = clientPayment.getClientRecord().getUser();
        List<ClientPaymentProduct> clientPaymentProducts = clientPayment.getClientPaymentProducts();

        int paymentSum = 0;
        for (ClientPaymentProduct item : clientPaymentProducts) {
            paymentSum += item.getPrice();
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("date", date);
        modelAndView.addObject("paymentSum", paymentSum);
        modelAndView.addObject("client", client);
        modelAndView.addObject("paymentProducts", clientPaymentProducts);
        modelAndView.addObject("menuSelect", "payment");
        modelAndView.setViewName("cabinet/client/payment/product");
        return modelAndView;
    }

}
