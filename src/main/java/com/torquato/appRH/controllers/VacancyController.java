package com.torquato.appRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.services.CandidateService;
import com.torquato.appRH.services.VacancyService;

@Controller
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private CandidateService candidateService;

    // Formulário para cadastrar uma vaga
    @GetMapping("/cadastrarVaga")
    public String form() {
        return "vaga/formVaga";
    }

    // Cadastro de uma nova vaga
    @PostMapping("/cadastrarVaga")
    public String createVacancy(@Validated Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/vaga/formVaga";
        }

        vacancyService.saveVacancy(vacancy);
        attributes.addFlashAttribute("mensagem", "Vaga cadastrada com sucesso!");
        return "redirect:/vaga/formVaga";
    }

    // Listagem de vagas
    @GetMapping
    public ModelAndView listVacancies() {
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        mv.addObject("vacancies", vacancyService.getAllVacancy());
        return mv;
    }

    // Detalhes da vaga e candidatos associados
    @GetMapping("/{code}")
    public ModelAndView vacancyDetails(@PathVariable Long code) {
        Vacancy vacancy = vacancyService.getVacancyByCode(code);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vacancy", vacancy);
        mv.addObject("candidates", candidateService.getCandidatesByVacancy(vacancy));
        return mv;
    }

    // Deletar uma vaga
    @GetMapping("/deletar/{code}")
    public String deleteVacancy(@PathVariable Long code) {
        vacancyService.deleteVacancy(code);
        return "redirect:/vagas";
    }


    // Editar uma vaga
    @GetMapping("/editar/{code}")
    public ModelAndView editVacancy(@PathVariable Long code) {
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        mv.addObject("vacancy", vacancyService.getVacancyByCode(code));
        return mv;
    }

    @PostMapping("/editar")
    public String updateVacancy(@Validated Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
        vacancyService.saveVacancy(vacancy);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");
        return "redirect:/vagas/" + vacancy.getCode();
    }
}