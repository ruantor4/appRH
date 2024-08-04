package com.torquato.appRH.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Vacancy;

@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

}
