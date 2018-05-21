package ru.myrecord.front.service.impl.organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.myrecord.front.data.dao.organisation.OrgTarifDAO;
import ru.myrecord.front.data.model.entities.organisation.OrgTarif;
import ru.myrecord.front.service.iface.organisation.OrgTarifService;

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
    public OrgTarif getTarifById(Integer id) {
        if (id == null)
            return null;
        else
            return orgTarifDAO.findById(id).get();
    }

    @Override
    public void addTarif(OrgTarif orgTarif) {
        orgTarifDAO.save(orgTarif);
    }
}
