package com.torquato.appRH.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.torquato.appRH.models.Employee;
import com.torquato.appRH.services.EmployeeService;

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;


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
		employeeService.saveEmployee(employee);
		attributes.addFlashAttribute("mensagem", "Funcionário cadastrado com sucesso!");
		return "redirect:/cadastrarFuncionario";
	}

	// LISTAR FUNCIONARIOS
	@GetMapping("/funcionarios")
	public ModelAndView listaFuncionarios() {
		ModelAndView mv = new ModelAndView("funcionario/listaFuncionario");
		mv.addObject("employees", employeeService.getAllEmployees());
		return mv;
	}

	// EDITAR FUNCIONARIO
	@GetMapping("/editar-funcionario")
	public ModelAndView editarFuncionario(long id) {
		ModelAndView mv = new ModelAndView("funcionario/update-funcionario");
		mv.addObject("employee", employeeService.getEmployeeById(id));
		return mv;
	}

	@PostMapping("/editar-funcionario")
	public String updateFuncionario(@Validated Employee employee, BindingResult result, RedirectAttributes attributes) {
		employeeService.saveEmployee(employee);
		attributes.addFlashAttribute("success", "Funcionário atualizado com sucesso!");
		return "redirect:/dependentes/" + employee.getId();
	}

	// DELETAR FUNCIONARIO
	@GetMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/funcionarios";
	}
}
