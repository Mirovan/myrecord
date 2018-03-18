package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.UserSalaryDAO;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserSalary;
import ru.myrecord.front.service.iface.UserSalaryService;

import java.util.Set;

@Service("userSelaryService")
public class UserSalaryServiceImpl implements UserSalaryService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("userSalaryDAO")
    private UserSalaryDAO userSalaryDAO;

    @Override
    public void add(UserSalary userSalary) {
        userSalaryDAO.save(userSalary);
    }

    @Override
    public UserSalary findByUser(User user) {
        Set<UserSalary> userSalaries = userSalaryDAO.findByUserOrderByStartdateDesc(user);
        if (userSalaries != null && userSalaries.iterator().hasNext())
            return userSalaries.iterator().next();
        else
            return null;
    }
}
