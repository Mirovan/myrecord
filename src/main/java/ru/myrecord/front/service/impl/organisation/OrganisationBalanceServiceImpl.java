package ru.myrecord.front.service.impl.organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.organisation.OrganisationBalanceDAO;
import ru.myrecord.front.data.model.entities.organisation.OrgTarif;
import ru.myrecord.front.data.model.entities.organisation.OrganisationBalance;
import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.organisation.OrgTarifService;
import ru.myrecord.front.service.iface.organisation.OrganisationBalanceService;

import java.time.LocalDate;
import java.util.List;


@Service("organisationBalanceService")
public class OrganisationBalanceServiceImpl implements OrganisationBalanceService {

    @Autowired
    @Qualifier("organisationBalanceDAO")
    private OrganisationBalanceDAO balanceDao;

    @Autowired
    @Qualifier("orgTarifService")
    private OrgTarifService orgTarifService;

    @Override
    public void addPayment(Payment payment) {
        OrganisationBalance organisationBalanceDao = balanceDao.findFirstByUserOrderByIdDesc(payment.getUser());
        OrganisationBalance organisationBalance = new OrganisationBalance();
        if(organisationBalanceDao == null) {
            organisationBalance.setBalance(payment.getPrice());
            organisationBalance.setUser(payment.getUser());
            List<OrgTarif> orgTarifs = orgTarifService.getTarifs();
            organisationBalance.setOrgTarif(orgTarifs.size() > 0 ? orgTarifs.get(0) : new OrgTarif());
            organisationBalance.setExpDate(LocalDate.now().minusDays(1));
        } else {
            organisationBalance.setUser(payment.getUser());
            organisationBalance.setOrgTarif(organisationBalanceDao.getOrgTarif());
            organisationBalance.setBalance(payment.getPrice() + organisationBalanceDao.getBalance());
            organisationBalance.setExpDate(organisationBalanceDao.getExpDate());
        }
        balanceDao.save(organisationBalance);
    }

    @Override
    public void updateBalance(User user) {
        OrganisationBalance organisationBalanceDao = balanceDao.findFirstByUserOrderByIdDesc(user);
        if (organisationBalanceDao.getExpDate().isBefore(LocalDate.now())) {
            if (organisationBalanceDao.getBalance() >= organisationBalanceDao.getOrgTarif().getPrice()) {
                OrganisationBalance balance = new OrganisationBalance();
                balance.setBalance(organisationBalanceDao.getBalance() - organisationBalanceDao.getOrgTarif().getPrice());
                balance.setExpDate(organisationBalanceDao.getExpDate().plusMonths(1));
                balance.setOrgTarif(organisationBalanceDao.getOrgTarif());
                balance.setUser(organisationBalanceDao.getUser());
            }
            System.out.println(String.format("Срок действия окончен, для пользователя %s", user.getName()+" " + user.getSirname()));

        } else {
            System.out.println(String.format("Все ок для пользователя %s", user.getName()+" " + user.getSirname()));
        }

    }

    @Override
    public OrganisationBalance getBalanceByUser(User user) {
        return balanceDao.findFirstByUserOrderByIdDesc(user);
    }

    @Override
    public boolean isActive(User user) {
        OrganisationBalance organisationBalance = getBalanceByUser(user);
        if(organisationBalance.getExpDate().isAfter(LocalDate.now()))
            return true;
        return false;
    }
}
