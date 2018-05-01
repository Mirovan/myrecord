package ru.myrecord.front.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import ru.myrecord.front.data.model.entities.organisation.OrganisationBalance;
import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.service.iface.UserService;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.organisation.OrganisationBalanceService;
import ru.myrecord.front.service.iface.organisation.PaymentsService;

import java.security.Principal;
import java.time.LocalDate;

@Controller
@RequestMapping(value = "/cabinet/billing")
public class BillingController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private OrganisationBalanceService balanceService;

    @RequestMapping(value={"", "/", "/index/", "/index"}, method = RequestMethod.GET)
    public ModelAndView showBalance(Principal principal) {
        User user = userService.findUserByEmail(principal.getName());

        ModelAndView modelAndView = new ModelAndView();
        OrganisationBalance balance = balanceService.getBalanceByUser(user);
        if (balance == null) {
            balanceService.createBalance(user);
            balance = balanceService.getBalanceByUser(user);
        }
        modelAndView.addObject( "balance", balance);
        modelAndView.addObject("payments", paymentsService.getPayments(user));
        modelAndView.setViewName("cabinet/billing/index");
        return modelAndView;
    }

    @RequestMapping(value={"/pay/"}, method = RequestMethod.GET)
    public ModelAndView payPage(Principal principal) {
        ModelAndView modelAndView = new ModelAndView();
        User user = userService.findUserByEmail(principal.getName());
        modelAndView.addObject( "balance", balanceService.getBalanceByUser(user));
        modelAndView.setViewName("cabinet/billing/pay");
        return modelAndView;
    }

    @RequestMapping(value={"/pay/"}, method = RequestMethod.POST)
    public ModelAndView pay(Principal principal, float amount) {
        User user = userService.findUserByEmail( principal.getName() );

        Payment payment = new Payment();
        payment.setPaymentDate(LocalDate.now());
        payment.setPrice(amount);
        payment.setUser(user);

        paymentsService.addPayment(payment);

        return new ModelAndView("redirect:/cabinet/billing/index");
//        modelAndView.addObject( "balance", balanceService.getBalanceByUser(user));
//        modelAndView.addObject("payments", paymentsService.getPayments(user));
//        modelAndView.setViewName("cabinet/billing/index");
//        return modelAndView;
    }

}
