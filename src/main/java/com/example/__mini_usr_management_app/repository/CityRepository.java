package com.example.__mini_usr_management_app.repository;

import com.example.__mini_usr_management_app.entity.City;
import com.example.__mini_usr_management_app.entity.State;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;
import java.util.List;

public interface CityRepository extends JpaRepository<State , Serializable> {

    /**
     *  select * from city where state_id = ?
     */
    public List<City> findByStateId(Integer stateId);
}
