package com.torquato.appRH.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;


@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	Candidate findByRg(String rg);

	Candidate findById(long id);

	Iterable<Candidate> findByVacancy(Vacancy vacancy);

	
}
