package ru.myrecord.front.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.OrgTarifDAO;
import ru.myrecord.front.data.model.entities.OrgTarif;
import ru.myrecord.front.service.iface.OrgTarifService;

import java.util.List;

@Service("orgTarifService")
public class OrgTarifServiceImpl implements OrgTarifService {

    @Autowired
    @Qualifier("orgTarifDAO")
    private OrgTarifDAO orgTarifDAO;

    @Override
    public List<OrgTarif> getTarifs() {
        return orgTarifDAO.findAll();
    }

    @Override
    public OrgTarif getTarifById(int id) {
        return orgTarifDAO.findById(id);
    }

    @Override
    public void addTarif(OrgTarif orgTarif) {
        orgTarifDAO.save(orgTarif);
    }
}
