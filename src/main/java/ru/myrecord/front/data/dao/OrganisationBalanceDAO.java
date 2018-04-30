package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.OrganisationBalance;
import ru.myrecord.front.data.model.entities.User;


@Repository("organisationBalanceDAO")
public interface OrganisationBalanceDAO extends JpaRepository<OrganisationBalance, Integer> {

    OrganisationBalance findFirstByUserOrderByIdDesc (User user);


}
