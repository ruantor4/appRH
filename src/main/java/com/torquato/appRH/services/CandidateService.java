package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.repositories.CandidateRepository;

@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    public Candidate saveCandidate(Candidate candidate, Vacancy vacancy) {
        candidate.setVacancy(vacancy);
        return candidateRepository.save(candidate);

    }

    public Candidate getCandidateByRg(String rg) {
        return candidateRepository.findByRg(rg);
    }

    public Candidate saveCandidateByRg(String rg) {
        return candidateRepository.findByRg(rg);

    }

    public Iterable<Candidate> getCandidatesByVacancy(Vacancy vacancy){
        return candidateRepository.findByVacancy(vacancy);
    }

    public void deleteCandidate(String rg){
        Candidate candidate = candidateRepository.findByRg(rg);
        if (candidate != null) {
            candidateRepository.delete(candidate);
        }
    }

}
