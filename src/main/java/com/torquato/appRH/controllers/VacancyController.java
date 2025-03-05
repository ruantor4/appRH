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
    @GetMapping("/vagas")
    public ModelAndView listVacancies() {
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        mv.addObject("vacancies", vacancyService.getAllVacancy());
        return mv;
    }

    // Detalhes da vaga e candidatos associados
    @GetMapping("/vagas/{code}")
    public ModelAndView vacancyDetails(@PathVariable Long code) {
        Vacancy vacancy = vacancyService.getVacancyByCode(code);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vacancy", vacancy);
        
        Iterable<Candidate> candidates = candidateService.getCandidatesByVacancy(vacancy);
        mv.addObject("candidates", candidates);
        return mv;
    }

    // Deletar uma vaga
    @GetMapping("/deletarVaga")
    public String deleteVacancy(@PathVariable Long code) {
        vacancyService.deleteVacancy(code);
        return "redirect:/vagas";
    }


     // Adicionar candidato a uma vaga
     @PostMapping("/vagas/{code}")
     public String addCandidate(@PathVariable Long code, @Validated Candidate candidate, BindingResult result,
                                RedirectAttributes attributes) {
 
         if (result.hasErrors()) {
             attributes.addFlashAttribute("mensagem", "Verifique os campos");
             return "redirect:/vagas/" + code;
         }
 
         if (candidateService.getCandidateByRg(candidate.getRg()) != null) {
             attributes.addFlashAttribute("mensagem_erro", "RG duplicado, insira novamente");
             return "redirect:/vagas/" + code;
         }
 
         Vacancy vacancy = vacancyService.getVacancyByCode(code);
         candidateService.saveCandidate(candidate, vacancy);
         attributes.addFlashAttribute("mensagem", "Candidato adicionado com sucesso!");
         return "redirect:/vagas/" + code;
     }
 
     // Deletar candidato pelo RG
     @GetMapping("/deletarCandidato")
     public String deleteCandidate(@PathVariable String rg) {
         Candidate candidate = candidateService.getCandidateByRg(rg);
         if (candidate != null) {
             Long vacancyCode = candidate.getVacancy().getCode();
             candidateService.deleteCandidate(rg);
             return "redirect:/vagas/" + vacancyCode;
         }
         return "redirect:/vagas";
     }

    // Editar uma vaga
    @GetMapping("/editar-vaga")
    public ModelAndView editVacancy(@PathVariable Long code) {
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        Vacancy vacancy = vacancyService.getVacancyByCode(code);
        mv.addObject("vacancy", vacancy);
        return mv;
    }

    // POST do FORM que atualiza a vaga
    @PostMapping("/editar-vaga")
    public String updateVacancy(@Validated Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
        vacancyService.saveVacancy(vacancy);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");
        return "redirect:/vagas/" + vacancy.getCode();
    }
}