package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.OrganisationBalance;
import ru.myrecord.front.data.model.entities.Payment;
import ru.myrecord.front.data.model.entities.User;

public interface OrganisationBalanceService {


    void addPayment (Payment payment);
    void updateBalance(User user);
    OrganisationBalance getBalanceByUser(User user);

}
