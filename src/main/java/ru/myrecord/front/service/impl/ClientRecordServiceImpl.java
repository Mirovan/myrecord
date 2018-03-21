package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.ClientRecordDAO;
import ru.myrecord.front.data.model.entities.ClientRecord;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ClientRecordService;
import ru.myrecord.front.service.iface.UserService;

import java.util.Random;

@Service("clientRecordService")
public class ClientRecordServiceImpl implements ClientRecordService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("clientRecordDAO")
    private ClientRecordDAO clientRecordDAO;

    @Autowired
    private UserService userService;

    @Override
    public ClientRecord add(ClientRecord clientRecord, User ownerUser) {
        User client = clientRecord.getUser();
        if (client.getEmail() == null) {
            String email = client.getPhone() + "@mail.ru";
            clientRecord.getUser().setEmail(email);
        }

        if (client.getOwnerUser() == null)
            client.setOwnerUser(ownerUser);

        if (client.getPass() == null)
            client.setPass( userService.generatePassword((new Random(10000000L)).toString()) );

        clientRecord.setActive(true);

        return clientRecordDAO.saveAndFlush(clientRecord);
    }

    @Override
    public ClientRecord findByUser(User user) {
        return null;
    }
}
