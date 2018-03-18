package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserSalary;

public interface UserSalaryService {
    void add(UserSalary userSalary);
    UserSalary findByUser(User user); //Есть ли у юзера продукт и связь активна
}
