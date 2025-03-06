package com.torquato.appRH.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Vacancy;

/**
 * Repositório para operações de persistência da entidade {@link Vacancy}.
 * 
 * <p>
 * Esta interface estende {@link JpaRepository}, fornecendo métodos
 * para manipulação das vagas de emprego no banco de dados.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Repository
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

	/**
	 * Busca uma vaga de emprego pelo código.
	 * 
	 * @param code Código identificador da vaga.
	 * @return A vaga correspondente ou {@code null} se não encontrada.
	 */
	Vacancy findByCode(Long code);

}
