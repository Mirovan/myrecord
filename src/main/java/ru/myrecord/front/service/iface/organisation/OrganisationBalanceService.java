package ru.myrecord.front.service.iface.organisation;

import ru.myrecord.front.data.model.entities.organisation.OrganisationBalance;
import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.data.model.entities.User;

public interface OrganisationBalanceService {

    void createBalance(User user);
    void addPayment(Payment payment);
    void updateBalance(User user);
    OrganisationBalance getBalanceByUser(User user);
    boolean isActive(User user);

}
