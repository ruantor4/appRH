package com.torquato.appRH.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	Candidate findByRg(String rg);

	Iterable<Candidate> findByVacancy(Vacancy vacancy);

}
