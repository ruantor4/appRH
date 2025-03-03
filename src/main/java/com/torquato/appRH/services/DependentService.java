package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;
import com.torquato.appRH.repositories.DependentRepository;
import com.torquato.appRH.repositories.EmployeeRepository;

@Service
public class DependentService {

    @Autowired
    private DependentRepository dependentRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    public String saveDependent(long employeeId, Dependent dependent, BindingResult result,
            RedirectAttributes attributes) {
        if (result.hasErrors()) {
            attributes.addFlashAttribute("mensagem", "Verifique os campos");
            return "redirect:/funcionarios/" + employeeId + "/dependentes";
        }
        if (dependentRepository.findByCpf(dependent.getCpf()) != null) {
            attributes.addFlashAttribute("mensagem_erro", "CPF duplicado");
            return "redirect:/funcionarios/" + employeeId + "/dependentes";
        }
        Employee employee = employeeRepository.findById(employeeId);
        dependent.setEmployee(employee);
        dependentRepository.save(dependent);
        attributes.addFlashAttribute("mensagem", "Dependente adicionado com sucesso!");
        return "redirect:/funcionarios/" + employeeId + "/dependentes";
    }

    public String deleteDependent(String cpf) {
        Dependent dependent = dependentRepository.findByCpf(cpf);
        long employeeId = dependent.getEmployee().getId();
        dependentRepository.delete(dependent);
        return "redirect:/funcionarios/" + employeeId + "/dependentes";
    }

    public Iterable<Dependent> findDependentsByEmployee(Employee employee){
        return dependentRepository.findByEmployee(employee);
    }
}

