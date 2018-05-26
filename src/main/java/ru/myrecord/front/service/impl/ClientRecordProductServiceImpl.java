package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientRecordProductDAO;
import ru.myrecord.front.data.model.adapters.UserProductAdapter;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.ClientRecordProduct;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordProductService;
import ru.myrecord.front.service.iface.ProductService;
import ru.myrecord.front.service.iface.UserService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service("clientRecordProductService")
public class ClientRecordProductServiceImpl implements ClientRecordProductService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("clientRecordProductDAO")
    private ClientRecordProductDAO clientRecordProductDAO;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;


    @Override
    public void add(ClientRecordProduct clientRecordProduct) {
        clientRecordProductDAO.save(clientRecordProduct);
    }


    @Override
    public ClientRecordProduct findByClientRecord(ClientRecord clientRecord) {
        return null;
    }


    @Override
    public Set<ClientRecordProduct> findByDate(User ownerUser, LocalDate date) {
        LocalDateTime start = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 0, 0);
        LocalDateTime end = LocalDateTime.of(date.getYear(), date.getMonth(), date.getDayOfMonth(), 23, 59, 59);
        Set<User> workers = userService.findWorkersByOwner(ownerUser);
        return clientRecordProductDAO.findBySdateBetweenAndWorkerIn(start, end, workers);
    }


    @Override
    public List<UserProductAdapter> getClientsByDate(User ownerUser, LocalDate date) {
        List<UserProductAdapter> clients = new ArrayList<>();

        Set<ClientRecordProduct> clientRecords = findByDate(ownerUser, date);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for (ClientRecordProduct item : clientRecords) {
            UserProductAdapter userProduct = new UserProductAdapter(
                    item.getClientRecord().getUser(),
                    item.getProduct(),
                    item.getSdate().format(timeFormatter)
            );
            clients.add( userProduct );
        }
        return clients;
    }


    @Override
    public List<UserProductAdapter> getRemindClientsByDate(User ownerUser, LocalDate date) {
        List<UserProductAdapter> res = new ArrayList<>();

        //Находим все услуги
        Set<Product> products = productService.findProductsByOwnerUser(ownerUser);
        for (Product product: products) {
            //Находим всех кто был на услуге n-дней назад
            List<UserProductAdapter> clients = getClientsByDate(
                    ownerUser, date.minusDays(product.getRemindPeriod())
            );
            res.addAll(clients);
        }

        return res;
    }

}
