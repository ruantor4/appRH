package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.repositories.CandidateRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada aos candidatos.
 * 
 * <p>
 * Esta classe gerencia as operações de persistência dos candidatos, como
 * salvar,
 * buscar por RG, buscar candidatos por vaga e deletar candidatos.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Service
public class CandidateService {

    @Autowired
    private CandidateRepository candidateRepository;

    /**
     * Salva um novo candidato, associando-o a uma vaga.
     * 
     * @param candidate O candidato a ser salvo.
     * @param vacancy   A vaga à qual o candidato está se inscrevendo.
     * @return O candidato salvo.
     */
    public Candidate saveCandidate(Candidate candidate, Vacancy vacancy) {
        candidate.setVacancy(vacancy);
        return candidateRepository.save(candidate);
    }

    /**
     * Busca um candidato pelo número do RG.
     * 
     * @param rg O RG do candidato.
     * @return O candidato correspondente ou {@code null} se não encontrado.
     */
    public Candidate getCandidateByRg(String rg) {
        return candidateRepository.findByRg(rg);
    }

    /**
     * Busca e retorna um candidato com base no número do RG.
     * 
     * @param rg O RG do candidato.
     * @return O candidato correspondente ou {@code null} se não encontrado.
     */
    public Candidate saveCandidateByRg(String rg) {
        return candidateRepository.findByRg(rg);
    }

    /**
     * Retorna todos os candidatos que se inscreveram em uma vaga específica.
     * 
     * @param vacancy A vaga à qual os candidatos se inscreveram.
     * @return Uma lista iterável de candidatos associados à vaga.
     */
    public Iterable<Candidate> getCandidatesByVacancy(Vacancy vacancy) {
        return candidateRepository.findByVacancy(vacancy);
    }

    /**
     * Deleta um candidato com base no número do RG.
     * 
     * @param rg O RG do candidato a ser deletado.
     */
    public void deleteCandidate(String rg) {
        Candidate candidate = candidateRepository.findByRg(rg);
        if (candidate != null) {
            candidateRepository.delete(candidate);
        }
    }

}
