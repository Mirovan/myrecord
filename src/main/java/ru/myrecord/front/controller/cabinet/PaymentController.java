package ru.myrecord.front.controller.cabinet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.ClientRecord;
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


    @RequestMapping(value="/cabinet/clients/payment", method = RequestMethod.GET)
    public ModelAndView index(Principal principal) {
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

}
