package ru.myrecord.front.controller.cabinet.rest;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.myrecord.front.stubs.PaymentsStub;
import ru.myrecord.front.stubs.Utils;

import java.security.Principal;
import java.util.List;

@RestController
public class AdminRestController {


//    @RequestMapping(value="/admin/users/payments/", method = RequestMethod.GET)
//    public List<> getPayments(Principal principal) {
//
//        List<PaymentsStub> payments = Utils.getPayments();
//
//
//
//    }



    @RequestMapping(value="/admin/users/payments/{userId}", method = RequestMethod.GET)
    public List<PaymentsStub> getPaymentsByUser(Principal principal, @PathVariable int userId) {
        return Utils.getPayments();
    }

}
