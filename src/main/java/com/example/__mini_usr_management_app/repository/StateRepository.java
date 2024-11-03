package com.example.__mini_usr_management_app.repository;

import com.example.__mini_usr_management_app.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface StateRepository extends JpaRepository<State, Serializable> {
    /**
     *This method is used to retrive list of states bases on country
     *
     *  select * from country where country_id = ?
     */
    public List<State> findByCountryId(Integer countryId);
}
