package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.UserProductSalaryDAO;
import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProductSalary;
import ru.myrecord.front.service.iface.UserProductSalaryService;

import java.util.Set;

@Service("userProductSalaryService")
public class UserProductSalaryServiceImpl implements UserProductSalaryService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userProductSalaryDAO")
    private UserProductSalaryDAO userProductSalaryDAO;

    @Override
    public void add(UserProductSalary userProductSalary) {
        userProductSalaryDAO.save(userProductSalary);
    }

    @Override
    public void update(UserProductSalary userProductSalary) {
        userProductSalaryDAO.save(userProductSalary);
    }

    @Override
    public UserProductSalary findByWorkerAndProduct(User worker, Product product) {
        Set<UserProductSalary> userProductSalaries = userProductSalaryDAO.findByWorkerAndProductOrderByStartdateDesc(worker, product);
        if (userProductSalaries != null && userProductSalaries.iterator().hasNext())
            return userProductSalaries.iterator().next();
        else
            return null;
    }

}
