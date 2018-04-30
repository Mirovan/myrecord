package ru.myrecord.front.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.OrganisationBalanceDao;
import ru.myrecord.front.data.model.entities.OrganisationBalance;
import ru.myrecord.front.data.model.entities.Payment;
import ru.myrecord.front.data.model.entities.Tarif;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.OrganisationBalanceService;

import java.time.LocalDate;


@Service("organisationBalanceService")
public class OrganisationBalanceServiceImpl implements OrganisationBalanceService {

    @Autowired
    @Qualifier("organisationBalanceDao")
    private OrganisationBalanceDao balanceDao;

//    private Tarif

    @Override
    public void addPayment(Payment payment) {
        OrganisationBalance organisationBalanceDao = balanceDao.findTopByUserOrderByExpDateDesc(payment.getUser());
        OrganisationBalance organisationBalance = new OrganisationBalance();
        if(organisationBalanceDao == null) {
            organisationBalance.setBalance(payment.getPrice());
            organisationBalance.setUser(payment.getUser());
//            organisationBalance.setTarif();
        } else {
            organisationBalance.setBalance(payment.getPrice() + organisationBalanceDao.getBalance());
        }
        balanceDao.save(organisationBalance);
    }

    @Override
    public void updateBalance(User user) {
        OrganisationBalance organisationBalanceDao = balanceDao.findTopByUserOrderByExpDateDesc(user);
        if (organisationBalanceDao.getExpDate().isBefore(LocalDate.now())) {
            if (organisationBalanceDao.getBalance() >= organisationBalanceDao.getTarif().getPrice()) {
                OrganisationBalance balance = new OrganisationBalance();
                balance.setBalance(organisationBalanceDao.getBalance() - organisationBalanceDao.getTarif().getPrice());
                balance.setExpDate(organisationBalanceDao.getExpDate().plusMonths(1));
                balance.setTarif(organisationBalanceDao.getTarif());
                balance.setUser(organisationBalanceDao.getUser());
            }
            System.out.println(String.format("Срок действия окончен, для пользователя %s", user.getName()+" " + user.getSirname()));

        } else {
            System.out.println(String.format("Все ок для пользователя %s", user.getName()+" " + user.getSirname()));
        }

    }

    @Override
    public OrganisationBalance getBalanceByUser(User user) {
        return balanceDao.findTopByUserOrderByExpDateDesc(user);
    }
}
