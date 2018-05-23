package ru.myrecord.front.data.dao.organisation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.organisation.OrganisationBalance;
import ru.myrecord.front.data.model.entities.User;


@Repository("organisationBalanceDAO")
public interface OrganisationBalanceDAO extends JpaRepository<OrganisationBalance, Integer> {

    OrganisationBalance findFirstByUserOrderByIdDesc(User user);


}
