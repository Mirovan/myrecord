package ru.myrecord.front.data.dao.organisation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.organisation.OrgTarif;

@Repository("orgTarifDAO")
public interface OrgTarifDAO extends JpaRepository<OrgTarif, Integer> {

    OrgTarif findById(int id);

}
