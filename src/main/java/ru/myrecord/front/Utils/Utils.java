package ru.myrecord.front.Utils;

import java.security.Principal;

/**
 * Created by sbt-milovanov-mm on 01.12.2017.
 */
public class Utils {
    /**
     * Приоверка - равны ли пользователи
     * */
    public static Boolean userEquals(Long userId1, Long userId2) {
        return userId1.equals(userId2);
    }
}
