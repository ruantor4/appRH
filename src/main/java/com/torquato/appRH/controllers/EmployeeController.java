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

import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;
import com.torquato.appRH.repositories.DependentRepository;
import com.torquato.appRH.repositories.EmployeeRepository;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeRepository er;

	@Autowired
	private DependentRepository dr;

	// PAGINA DE CADASTRO

	@GetMapping("/cadastrarFuncionario")
	public String form() {
		return "funcionario/formFuncionario";

	}

	// VALIDAÇÃO

	@PostMapping("/cadastrarFuncionario")
	public String form(@Validated Employee employee, BindingResult result, RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/cadastrarFuncionario";
		}
		er.save(employee);
		attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";

	}

	// LISTAR FUNCIONARIOS

	@GetMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		Iterable<Employee> employees = er.findAll();
		mv.addObject("employees", employees);
		return mv;

	}

	// LISTAR DEPENDENTES

	@GetMapping("/dependentes/{id}")
	public ModelAndView dependentes(@PathVariable long id) {
		Employee employee = er.findById(id); 
		ModelAndView mv = new ModelAndView("/funcionario/dependentes"); 
		mv.addObject("employees", employee);

		Iterable<Dependent> dependents = dr.findByEmployee(employee);
		mv.addObject("dependents", dependents);
		return mv;
	}

	// ADICIONAR DEPENDENTES

	@PostMapping("/dependentes/{id}")
	public String dependentesPost(@PathVariable long id, Dependent dependent, BindingResult result,
			RedirectAttributes attributes) {
		if (result.hasErrors()) {
			attributes.addFlashAttribute("mensagem", "Verifique os campos");
			return "redirect:/dependentes";
		}
		if (dr.findByCpf(dependent.getCpf()) != null) {
			attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
			return "redirect:/dependentes/{id}";
		}

		Employee employee = er.findById(id);
		dependent.setEmployee(employee);

		dr.save(dependent);
		attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso!");
		return "redirect:/dependentes/{id}";
	}

	//DELETAR FUNCIONARIO
	
	@GetMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {
		Employee employee = er.findById(id);
		er.delete(employee);
		return "redirect:/funcionarios";

	}
	
	//EDITAR FUNCIONARIO

	@GetMapping("/editar-funcionario")
	public ModelAndView editarFuncionario(long id) {
		Employee employee = er.findById(id);
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("employee", employee);
		return mv;
	}

	@PostMapping("/editar-funcionario")
	public String updateFuncionario(@Validated Employee employee, BindingResult result, RedirectAttributes attributes) {

		er.save(employee);
		attributes.addFlashAttribute("success", "Funcionario adicionado com sucesso!");

		long idLong = employee.getId();
		String id = "" + idLong;
		return "redirect:/dependentes/" + id;
	}

	//DELETAR DEPENDENTE

	
	@GetMapping("/deletarDependente")
	public String deletarDependente(String cpf) {
		Dependent dependent = dr.findByCpf(cpf);

		Employee employee = dependent.getEmployee();
		String codigo = "" + employee.getId();

		dr.delete(dependent);
		return "redirect:/dependentes/" + codigo;

	}
}
