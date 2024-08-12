package com.torquato.appRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.repositories.CandidateRepository;
import com.torquato.appRH.repositories.VacancyRepository;

@Controller
public class VacancyController {

	@Autowired
	public VacancyRepository vr;
	@Autowired
	public CandidateRepository cr;

	// #ACESSO CADASTRAR VAGA

	@GetMapping("/cadastrarVaga")
	public String form() {
		return "vaga/formVaga";
	}

	// #VALIDAÇÃO PARA O CADASTRO

	@PostMapping("/cadastrarVaga")
	public String form(@Validated Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarVaga";
		}
		vr.save(vacancy);
		attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
		return "redirect:/cadastrarVaga";
	}

	// #LISTAR VAGAS

	@GetMapping("/vagas")
	public ModelAndView listaVaga() {
		ModelAndView mv = new ModelAndView("vaga/listaVaga");
		Iterable<Vacancy> vacancies = vr.findAll();
		mv.addObject("vacancies", vacancies);
		return mv;

	}

	// #DETALHES DA VAGA

	@GetMapping("/{code}")
	public ModelAndView detalhesVaga(@PathVariable Long code) {
		Vacancy vacancy = vr.findByCode(code);
		ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
		mv.addObject("vacancy", vacancy);

		Iterable<Candidate> candidates = cr.findByVacancy(vacancy);
		mv.addObject("candidates", candidates);

		return mv;
	}

	// #DELETAR VAGA

	@GetMapping("/deletarVaga")
	public String deletarVaga(Long code) {
		Vacancy vacancy = vr.findByCode(code);
		vr.delete(vacancy);
		return "redirect:/vagas";
	}

	// #ADICIONANDO CANDIDATO A VAGA

	@PostMapping("/{code}")
	public String detalhesVagaPost(@PathVariable Long code, @Validated Candidate candidate, BindingResult result,
			RedirectAttributes attributes) {

		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/{code}";
		}

		// RG VALIDAÇÃO
		if (cr.findByRg(candidate.getRg()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "RG duplicado, insira novamente");
			return "redirect:/{code}";
		}

		Vacancy vacancy = vr.findByCode(code);
		candidate.setVacancy(vacancy);
		cr.save(candidate);
		attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");

		return "redirect:/{code}";
	}

	// #DELETAR CANDIDATO RG

	@GetMapping("/deletarCandidato")
	public String deletarCandidato(String rg) {
		Candidate candidate = cr.findByRg(rg);
		Vacancy vacancy = candidate.getVacancy();
		String code = "" + vacancy.getCode();

		cr.delete(candidate);

		return "redirect:/" + code;
	}

	// #EDITAR VAGA

	@GetMapping("/editar-vaga")
	public ModelAndView editarVaga(Long code) {
		Vacancy vacancy = vr.findByCode(code);
		ModelAndView mv = new ModelAndView("vaga/update-vaga");
		mv.addObject("vacancy", vacancy);
		return mv;
	}

	@PostMapping("/editar-vaga")
	public String updateVaga(@Validated Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
		vr.save(vacancy);
		attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");

		long codeLong = vacancy.getCode();
		String code = "" + codeLong;

		return "redirect:/" + code;
	}

}