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

@Controller
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;

	@Autowired
	private DependentService dependentService;

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

	// Lista dependentes e detalhes do funcionario
	@GetMapping("/detalhesfuncionario/{id}")
	public ModelAndView detalhesFuncionario(@PathVariable long id) {
		Employee employee = employeeService.getEmployeeById(id);
		ModelAndView mv = new ModelAndView("/funcionario/dependentes");
		mv.addObject("employees", employee);

		Iterable<Dependent> dependents = dependentService.findDependentsByEmployee(employee);
		mv.addObject("dependents", dependents);
		return mv;
	}

	//Adiciona dependentes
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

	// DELETAR FUNCIONARIO
	@GetMapping("/deletarFuncionario")
	public String deletarFuncionario(long id) {
		employeeService.deleteEmployee(id);
		return "redirect:/funcionarios";
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

	// DELETE DEPENDENTS
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

