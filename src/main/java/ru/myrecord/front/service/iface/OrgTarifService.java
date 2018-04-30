package ru.myrecord.front.service.iface;

import ru.myrecord.front.data.model.entities.OrgTarif;

import java.util.List;

public interface OrgTarifService {

    List<OrgTarif> getTarifs();
    OrgTarif getTarifById(int id);
    void addTarif(OrgTarif orgTarif);

}
