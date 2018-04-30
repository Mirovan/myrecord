package ru.myrecord.front.service.impl.organisation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.organisation.PaymentsDAO;
import ru.myrecord.front.data.model.entities.organisation.Payment;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.organisation.OrganisationBalanceService;
import ru.myrecord.front.service.iface.organisation.PaymentsService;

import java.util.Set;

@Service("paymentService")
public class PaymentsServiceImpl implements PaymentsService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("paymentsDAO")
    private PaymentsDAO paymentsDAO;

    @Autowired
    @Qualifier("organisationBalanceService")
    private OrganisationBalanceService balanceService;

    @Override
    public void addPayment(Payment payment) {
        paymentsDAO.save(payment);
        System.out.println(String.format("Зачислено поступление %s для клиента %d %s", payment.getPrice(), payment.getUser().getId(), payment.getUser().getName() + " " + payment.getUser().getSirname()));
        balanceService.addPayment(payment);
    }

    @Override
    public Set<Payment> getPayments(User user) {
        return paymentsDAO.findByUser(user);
    }

    @Override
    public int removePayment(Payment payment) {
        try {
            paymentsDAO.delete(payment);
            return 0;
        } catch (Exception e) {
            return -1;
        }
    }
}
