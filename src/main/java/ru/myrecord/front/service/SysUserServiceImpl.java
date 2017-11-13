package ru.myrecord.front.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.myrecord.front.data.dao.SysUserDAO;
import ru.myrecord.front.data.model.SysUser;

/**
 * Сервис для работы с сущностью
 */
public class SysUserServiceImpl implements SysUserService {

    private static final Logger LOG = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserDAO sysUserDAO;


    @Override
    public void addElement(SysUser element) {

    }
}
