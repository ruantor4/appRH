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

/**
 * Controlador responsável pela gestão das vagas e candidatos no sistema.
 * 
 * <p>
 * Esta classe contém métodos para criar, listar, editar, deletar e associar
 * candidatos às vagas.
 * Além disso, proporciona a manipulação dos dados das vagas, incluindo
 * cadastro, edição e detalhes de cada vaga.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Controller
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private CandidateService candidateService;

    /**
     * Exibe o formulário para cadastro de uma nova vaga.
     * 
     * @return O nome da view do formulário de cadastro de vaga.
     */
    @GetMapping("/cadastrarVaga")
    public String form() {
        return "vaga/formVaga";
    }

    /**
     * Cadastra uma nova vaga no sistema.
     * 
     * @param vacancy    A vaga a ser cadastrada.
     * @param result     Contém os erros de validação, caso existam.
     * @param attributes Atributos para a exibição de mensagens na interface do
     *                   usuário.
     * @return Redirecionamento para o formulário de cadastro de vaga ou mensagem de
     *         erro.
     */
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

    /**
     * Lista todas as vagas cadastradas no sistema.
     * 
     * @return A view contendo a lista de vagas.
     */
    @GetMapping("/vagas")
    public ModelAndView listVacancies() {
        ModelAndView mv = new ModelAndView("vaga/listaVaga");
        mv.addObject("vacancies", vacancyService.getAllVacancy());
        return mv;
    }

    /**
     * Exibe os detalhes de uma vaga específica, incluindo os candidatos associados.
     * 
     * @param code O código da vaga.
     * @return A view contendo os detalhes da vaga e seus candidatos.
     */
    @GetMapping("/vagas/{code}")
    public ModelAndView vacancyDetails(@PathVariable Long code) {
        Vacancy vacancy = vacancyService.getVacancyByCode(code);
        ModelAndView mv = new ModelAndView("vaga/detalhesVaga");
        mv.addObject("vacancy", vacancy);

        Iterable<Candidate> candidates = candidateService.getCandidatesByVacancy(vacancy);
        mv.addObject("candidates", candidates);
        return mv;
    }

    /**
     * Deleta uma vaga do sistema.
     * 
     * @param code O código da vaga a ser deletada.
     * @return Redirecionamento para a lista de vagas.
     */
    @GetMapping("/deletarVaga")
    public String deleteVacancy(@PathVariable Long code) {
        vacancyService.deleteVacancy(code);
        return "redirect:/vagas";
    }

    /**
     * Adiciona um candidato a uma vaga existente.
     * 
     * @param code       O código da vaga à qual o candidato será adicionado.
     * @param candidate  O candidato a ser adicionado.
     * @param result     Contém os erros de validação, caso existam.
     * @param attributes Atributos para exibição de mensagens.
     * @return Redirecionamento para os detalhes da vaga ou mensagem de erro.
     */
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

    /**
     * Deleta um candidato de uma vaga usando o RG do candidato.
     * 
     * @param rg O RG do candidato a ser deletado.
     * @return Redirecionamento para a página de detalhes da vaga ou para a lista de
     *         vagas.
     */
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

    /**
     * Exibe o formulário de edição de uma vaga.
     * 
     * @param code O código da vaga a ser editada.
     * @return A view de edição da vaga.
     */
    @GetMapping("/editar-vaga")
    public ModelAndView editVacancy(@PathVariable Long code) {
        ModelAndView mv = new ModelAndView("vaga/update-vaga");
        Vacancy vacancy = vacancyService.getVacancyByCode(code);
        mv.addObject("vacancy", vacancy);
        return mv;
    }

    /**
     * Atualiza os dados de uma vaga existente no sistema.
     * 
     * @param vacancy    A vaga com os novos dados.
     * @param result     Contém os erros de validação, caso existam.
     * @param attributes Atributos para exibição de mensagens.
     * @return Redirecionamento para os detalhes da vaga ou mensagem de erro.
     */
    @PostMapping("/editar-vaga")
    public String updateVacancy(@Validated Vacancy vacancy, BindingResult result, RedirectAttributes attributes) {
        vacancyService.saveVacancy(vacancy);
        attributes.addFlashAttribute("success", "Vaga alterada com sucesso!");
        return "redirect:/vagas/" + vacancy.getCode();
    }
}
