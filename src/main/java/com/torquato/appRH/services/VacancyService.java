package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.repositories.VacancyRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada às vagas de emprego.
 * 
 * <p>
 * Esta classe gerencia as operações de persistência das vagas, como criar,
 * listar,
 * buscar detalhes de uma vaga e deletar uma vaga.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Service
public class VacancyService {

	@Autowired
	public VacancyRepository vacancyRepository;

	/**
	 * Salva uma nova vaga no banco de dados.
	 * 
	 * @param vacancy A vaga a ser salva.
	 * @return A vaga salva, incluindo qualquer alteração feita (ex: geração de ID).
	 */
	public Vacancy saveVacancy(Vacancy vacancy) {
		return vacancyRepository.save(vacancy);
	}

	/**
	 * Retorna todas as vagas cadastradas no banco de dados.
	 * 
	 * @return Uma lista iterável de todas as vagas.
	 */
	public Iterable<Vacancy> getAllVacancy() {
		return vacancyRepository.findAll();
	}

	/**
	 * Busca uma vaga pelo código único fornecido.
	 * 
	 * @param code O código da vaga a ser buscada.
	 * @return A vaga correspondente ao código fornecido, ou {@code null} se não
	 *         encontrada.
	 */
	public Vacancy getVacancyByCode(Long code) {
		return vacancyRepository.findByCode(code);
	}

	/**
	 * Deleta uma vaga com base no código fornecido.
	 * 
	 * @param code O código da vaga a ser deletada.
	 */
	public void deleteVacancy(Long code) {
		Vacancy vacancy = vacancyRepository.findByCode(code);
		if (vacancy != null)
			vacancyRepository.delete(vacancy);
	}
}
