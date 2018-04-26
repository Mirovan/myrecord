package ru.myrecord.front.stubs;

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


}
