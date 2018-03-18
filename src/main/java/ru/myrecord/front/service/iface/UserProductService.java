package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.Product;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.data.model.entities.UserProduct;

import java.util.Set;

public interface UserProductService {
    void add(UserProduct userProduct);
    void update(UserProduct userProduct);
    boolean hasUserProductActiveLink(User user, Product product); //Есть ли у юзера продукт и связь активна
    boolean hasUserProductAnyLink(User user, Product product); //Есть ли у юзера продукт и связь неактивна
    UserProduct findByUserAndProductActiveLink(User user, Product product); //Поиск юзера-продукта с активной связью
    UserProduct findByUserAndProductAnyLink(User user, Product product); //Поиск юзера-продукты даже неактивного
    Set<UserProduct> findByProductActiveLink(Product product);
    Set<UserProduct> findByProductAnyLink(Product product);
}
