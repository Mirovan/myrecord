package ru.myrecord.front.controller.cabinet.rest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.Utils.stubs.PaymentsStub;
import ru.myrecord.front.Utils.stubs.Utils;
import ru.myrecord.front.data.model.entities.OrganisationBalance;
import ru.myrecord.front.data.model.entities.Payment;
import ru.myrecord.front.service.iface.OrganisationBalanceService;
import ru.myrecord.front.service.iface.PaymentsService;
import ru.myrecord.front.service.iface.UserService;

import java.security.Principal;
import java.util.List;
import java.util.Set;

@RestController
public class AdminRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PaymentsService paymentsService;

    @Autowired
    private OrganisationBalanceService balanceService;

//    @RequestMapping(value="/admin/users/payments/", method = RequestMethod.GET)
//    public List<> getPayments(Principal principal) {
//
//        List<PaymentsStub> payments = Utils.getPayments();
//
//
//
//    }



//    @RequestMapping(value="/admin/users/payments/{userId}", method = RequestMethod.GET)
//    public List<PaymentsStub> getPaymentsByUser(Principal principal, @PathVariable int userId) {
//        return Utils.getPayments();
//    }



    @RequestMapping(value="/admin/users/payments/{userId}", method = RequestMethod.GET)
    public Set<Payment> getPaymentsByUser(Principal principal, @PathVariable int userId) {
        Utils.generatePayments(userService, paymentsService, userId);
        return paymentsService.getPayments(userService.findUserById(userId));
    }

    @RequestMapping(value="/admin/users/balance/{userId}", method = RequestMethod.GET)
    public OrganisationBalance updateBalanceByUser(Principal principal, @PathVariable int userId) {
        return  balanceService.getBalanceByUser(userService.findUserById(userId));
    }


}
