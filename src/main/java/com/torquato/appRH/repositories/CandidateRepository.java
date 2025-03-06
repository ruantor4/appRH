package com.torquato.appRH.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;

/**
 * Repositório para operações de persistência da entidade {@link Candidate}.
 * 
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * para manipulação dos candidatos no banco de dados.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Long> {

	/**
	 * Busca um candidato pelo número do RG.
	 * 
	 * @param rg RG do candidato.
	 * @return O candidato correspondente ou {@code null} se não encontrado.
	 */
	Candidate findByRg(String rg);

	/**
	 * Busca um candidato pelo ID.
	 * 
	 * @param id ID do candidato.
	 * @return O candidato correspondente ou {@code null} se não encontrado.
	 */
	Candidate findById(long id);

	/**
	 * Retorna todos os candidatos que estão associados a uma determinada vaga.
	 * 
	 * @param vacancy A vaga para a qual os candidatos se inscreveram.
	 * @return Uma lista iterável de candidatos vinculados à vaga.
	 */
	Iterable<Candidate> findByVacancy(Vacancy vacancy);
}