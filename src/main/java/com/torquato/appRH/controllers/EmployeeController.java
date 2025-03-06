package com.torquato.appRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;
import com.torquato.appRH.services.DependentService;
import com.torquato.appRH.services.EmployeeService;

/**
 * Controlador responsável pela gestão dos funcionários e seus dependentes.
 * 
 * <p>
 * Esta classe contém métodos para criar, listar, editar, deletar funcionários e
 * gerenciar seus dependentes.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DependentService dependentService;

	/**
	 * Exibe o formulário para cadastro de um novo funcionário.
	 * 
	 * @return A view do formulário de cadastro de funcionário.
	 */
	@GetMapping("/cadastrarFuncionario")
	public String form() {
		return "funcionario/formFuncionario";
	}

	/**
	 * Cadastra um novo funcionário no sistema.
	 * 
	 * @param employee   O funcionário a ser cadastrado.
	 * @param result     Contém os erros de validação, caso existam.
	 * @param attributes Atributos para exibição de mensagens.
	 * @return Redirecionamento para o formulário de cadastro de funcionário ou
	 *         mensagem de erro.
	 */
	@PostMapping("/cadastrarFuncionario")
	public String form(@Validated Employee employee, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}
		employeeService.saveEmployee(employee);
		attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";
	}

	/**
	 * Lista todos os funcionários cadastrados no sistema.
	 * 
	 * @return A view contendo a lista de funcionários.
	 */
	@GetMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		mv.addObject("employees", employeeService.getAllEmployees());
		return mv;
	}

	/**
	 * Exibe os detalhes de um funcionário, incluindo seus dependentes.
	 * 
	 * @param id O ID do funcionário.
	 * @return A view com os detalhes do funcionário e sua lista de dependentes.
	 */
	@GetMapping("/detalhesfuncionario/{id}")
	public ModelAndView detalhesFuncionario(@PathVariable long id) {
		Employee employee = employeeService.getEmployeeById(id);
		ModelAndView mv = new ModelAndView("/funcionario/dependentes");
		mv.addObject("employees", employee);

		Iterable<Dependent> dependents = dependentService.findDependentsByEmployee(employee);
		mv.addObject("dependents", dependents);
		return mv;
	}

	/**
	 * Adiciona um dependente a um funcionário existente.
	 * 
	 * @param id         O ID do funcionário.
	 * @param dependent  O dependente a ser adicionado.
	 * @param result     Contém os erros de validação, caso existam.
	 * @param attributes Atributos para exibição de mensagens.
	 * @return Redirecionamento para os detalhes do funcionário ou mensagem de erro.
	 */
	@PostMapping("/detalhesfuncionario/{id}")
	public String detalhesFuncionarioPost(@PathVariable("id") long id, Dependent dependent,
			BindingResult result, RedirectAttributes attributes) {
		// Verifica se há erros no formulário
		if (result.hasErrors()) {
			attributes.addFlashAttribute("message", "Verifique os campos!");
			return "redirect:/detalhes-funcionario/" + id;
		}

		// Chama o serviço para adicionar o dependente
		String message = dependentService.addDependent(id, dependent);

		// Define a mensagem conforme o retorno do serviço
		if ("CPF duplicado".equals(message) || "Funcionário não encontrado".equals(message)) {
			attributes.addFlashAttribute("error_message", message);
		} else {
			attributes.addFlashAttribute("message", message);
		}

		return "redirect:/detalhes-funcionario/" + id;
	}

	/**
	 * Deleta um funcionário do sistema.
	 * 
	 * @param id O ID do funcionário a ser deletado.
	 * @return Redirecionamento para a lista de funcionários.
	 */
	@GetMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/funcionarios";
	}

	/**
	 * Exibe o formulário de edição de um funcionário.
	 * 
	 * @param id O ID do funcionário a ser editado.
	 * @return A view para edição do funcionário.
	 */
	@GetMapping("/editar-funcionario")
	public ModelAndView editarFuncionario(long id) {
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("employee", employeeService.getEmployeeById(id));
		return mv;
	}

	/**
	 * Atualiza os dados de um funcionário existente no sistema.
	 * 
	 * @param employee   O funcionário com os novos dados.
	 * @param result     Contém os erros de validação, caso existam.
	 * @param attributes Atributos para exibição de mensagens.
	 * @return Redirecionamento para os detalhes do funcionário ou mensagem de erro.
	 */
	@PostMapping("/editar-funcionario")
	public String updateFuncionario(@Validated Employee employee, BindingResult result, RedirectAttributes attributes) {
		employeeService.saveEmployee(employee);
		attributes.addFlashAttribute("success", "Funcionário atualizado com sucesso!");
		return "redirect:/dependentes/" + employee.getId();
	}

	/**
	 * Deleta um dependente de um funcionário.
	 * 
	 * @param cpf        O CPF do dependente a ser deletado.
	 * @param attributes Atributos para exibição de mensagens.
	 * @return Redirecionamento para a página de detalhes do funcionário ou erro.
	 */
	@GetMapping("/deletarDependente")
	public String deleteDependent(@RequestParam("cpf") String cpf, RedirectAttributes attributes) {
		try {
			Long code = dependentService.deleteDependent(cpf);
			attributes.addFlashAttribute("message", "Dependente removido com sucesso!");
			return "redirect:/employee-details/" + code;
		} catch (RuntimeException e) {
			attributes.addFlashAttribute("error_message", e.getMessage());
			return "redirect:/detalhes-funcionario";
		}
	}
}
