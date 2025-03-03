package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.repositories.VacancyRepository;

@Service
public class VacancyService {

	@Autowired
	public VacancyRepository vacancyRepository;

	// #CREATE VACANCY
	public Vacancy saveVacancy(Vacancy vacancy) {
		return vacancyRepository.save(vacancy);
	}

	// #LIST VAGAS

	public Iterable<Vacancy> getAllVacancy() {
		return vacancyRepository.findAll();
	}

	// #DETALHES DA VAGA

	public Vacancy getVacancyByCode(Long code) {
		return vacancyRepository.findByCode(code);
	}

	// #DELETAR VAGA

	public void deleteVacancy(Long code) {
		Vacancy vacancy = vacancyRepository.findByCode(code);
		if (vacancy != null)
			vacancyRepository.delete(vacancy);
	}

}