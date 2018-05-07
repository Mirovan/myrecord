package ru.myrecord.front.Utils.stubs;

import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.service.iface.organisation.PaymentsService;
import ru.myrecord.front.service.iface.UserService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Utils {


    public static List<PaymentsStub> getPayments() {
        List<PaymentsStub> list = new ArrayList<>();

        PaymentsStub paymentsStub = new PaymentsStub();
        paymentsStub.setId(0);
        paymentsStub.setPayment(1100.00f);
        paymentsStub.setTarif(1000.00f);
        paymentsStub.setPaymentDate(LocalDate.of(2018,1,12));
        list.add(paymentsStub);

        paymentsStub = new PaymentsStub();
        paymentsStub.setId(1);
        paymentsStub.setPayment(1000.00f);
        paymentsStub.setTarif(1000.00f);
        paymentsStub.setPaymentDate(LocalDate.of(2018,2,3));
        list.add(paymentsStub);

        paymentsStub = new PaymentsStub();
        paymentsStub.setId(1);
        paymentsStub.setPayment(1245.00f);
        paymentsStub.setTarif(1000.00f);
        paymentsStub.setPaymentDate(LocalDate.of(2018,3,10));
        list.add(paymentsStub);

        return list;
    }

    public static void generatePayments(UserService userService, PaymentsService paymentsService, int id) {

        LocalDate localDate = LocalDate.of(2018, 1, 12);
        Payment payment = new Payment();
        payment.setId(0);
        payment.setPrice(1000.0f);
        payment.setPaymentDate(localDate);
//        payment.setUser(userService.findByRole(UserRoles.SYSUSER).stream().findFirst().get());
        payment.setUser(userService.findUserById(id));

        paymentsService.addPayment(payment);

        payment = new Payment();
        localDate = LocalDate.of(2018, 2, 12);
        payment.setId(0);
        payment.setPrice(1000.0f);
        payment.setPaymentDate(localDate);
//        payment.setUser(userService.findByRole(UserRoles.SYSUSER).stream().findFirst().get());
        payment.setUser(userService.findUserById(id));
        paymentsService.addPayment(payment);

        payment = new Payment();
        localDate = LocalDate.of(2018, 3, 12);
        payment.setId(0);
        payment.setPrice(1000.0f);
        payment.setPaymentDate(localDate);
//        payment.setUser(userService.findByRole(UserRoles.SYSUSER).stream().findFirst().get());
        payment.setUser(userService.findUserById(id));
        paymentsService.addPayment(payment);

        payment = new Payment();
        localDate = LocalDate.of(2018, 4, 12);
        payment.setId(0);
        payment.setPrice(1000.0f);
        payment.setPaymentDate(localDate);
//        payment.setUser(userService.findByRole(UserRoles.SYSUSER).stream().findFirst().get());
        payment.setUser(userService.findUserById(id));
        paymentsService.addPayment(payment);

    }



}
