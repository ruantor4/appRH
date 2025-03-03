package com.torquato.appRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.torquato.appRH.models.Candidate;
import com.torquato.appRH.models.Vacancy;
import com.torquato.appRH.services.CandidateService;
import com.torquato.appRH.services.VacancyService;

public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private VacancyService vacancyService;



     // Adicionar candidato a uma vaga
    @PostMapping("/{code}/candidato")
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
    @GetMapping("/candidato/deletar/{rg}")
    public String deleteCandidate(@PathVariable String rg) {
        Candidate candidate = candidateService.getCandidateByRg(rg);
        if (candidate != null) {
            Long vacancyCode = candidate.getVacancy().getCode();
            candidateService.deleteCandidate(rg);
            return "redirect:/vagas/" + vacancyCode;
        }
        return "redirect:/vagas";
    }
    
}
