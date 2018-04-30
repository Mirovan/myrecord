package ru.myrecord.front.service.iface.organisation;

import ru.myrecord.front.data.model.entities.organisation.OrgTarif;

import java.util.List;

public interface OrgTarifService {

    List<OrgTarif> getTarifs();
    OrgTarif getTarifById(int id);
    void addTarif(OrgTarif orgTarif);

}
