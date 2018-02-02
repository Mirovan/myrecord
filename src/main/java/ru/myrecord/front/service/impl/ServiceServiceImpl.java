package ru.myrecord.front.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.myrecord.front.data.dao.ServiceDAO;
import ru.myrecord.front.data.model.entities.Room;
import ru.myrecord.front.data.model.entities.Service;
import ru.myrecord.front.data.model.entities.User;
import ru.myrecord.front.service.iface.ServiceService;

import java.util.Set;

@org.springframework.stereotype.Service("serviceService")
public class ServiceServiceImpl implements ServiceService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    @Qualifier("serviceDAO")
    private ServiceDAO serviceDAO;

    @Override
    public Service findServiceById(Integer id) {
        return serviceDAO.findById(id);
    }

    @Override
    public Set<Service> findServicesByUser(User user) {
        return serviceDAO.findByUserAndActiveTrueOrderByIdAsc(user);
    }

    @Override
    public Set<Service> findServicesByRoom(Room room) {
        return serviceDAO.findByRoomAndActiveTrueOrderByIdAsc(room);
    }

    @Override
    public void add(Service service) {
        serviceDAO.save(service);
    }

    @Override
    public void update(Service service) {
        serviceDAO.save(service);
    }

}
