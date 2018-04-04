package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.*;

import java.security.Principal;
import java.time.LocalDate;
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


    /**
     * Страница всех записей с отображением кнопки для оплаты
     * */
    @RequestMapping(value="/cabinet/clients/payment/", method = RequestMethod.GET)
    public ModelAndView showRecords(Principal principal) {
        LocalDate date = LocalDate.now();
        Set<ClientRecord> clientRecords = clientRecordService.findByDate(date);

        for (ClientRecord item : clientRecords) {
            User user = new User(item.getUser().getId(), item.getUser().getName(), item.getUser().getSirname());
            item.setUser(user);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("records", clientRecords);
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

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("record", clientRecord);
        modelAndView.addObject("products", products);
        modelAndView.setViewName("cabinet/client/payment/record");
        return modelAndView;
    }


    /**
     * Сохраняем оплату
     * */
    @RequestMapping(value="/cabinet/clients/payment/pay/", method = RequestMethod.POST)
    public ModelAndView showPaymentFormPost(@RequestParam Integer recordId, Principal principal) {
        LocalDate date = LocalDate.now();

        return( new ModelAndView("redirect: ") );
    }


}
