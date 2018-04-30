package ru.myrecord.front.data.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.myrecord.front.data.model.entities.OrganisationBalance;
import ru.myrecord.front.data.model.entities.User;


@Repository("organisationBalanceDao")
public interface OrganisationBalanceDao extends JpaRepository<OrganisationBalance, Integer> {

    OrganisationBalance findTopByUserOrderByExpDateDesc(User user);


}
