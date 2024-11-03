package com.example.__mini_usr_management_app.repository;

import com.example.__mini_usr_management_app.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.Serializable;

public interface CountryRepository extends JpaRepository<Country, Serializable> {
}
