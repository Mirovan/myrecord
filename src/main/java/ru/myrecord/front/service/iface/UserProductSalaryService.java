package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProductSalary;

public interface UserProductSalaryService {
    void add(UserProductSalary userProductSalary);
    void update(UserProductSalary userProductSalary);
    UserProductSalary findByUserAndProduct(User user, Product product);
}
