package com.torquato.appRH.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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

    public String addDependent(Long id, Dependent dependent) {
        // Verifica se já existe um dependente com o mesmo CPF
        if (dependentRepository.findByCpf(dependent.getCpf()) != null) {
            return "CPF duplicado";
        }

        // Busca o funcionário pelo ID usando Optional para evitar NullPointerException
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);
        if (optionalEmployee.isEmpty()) {
            return "Funcionário não encontrado";
        }

        // Associa o dependente ao funcionário e salva
        Employee employee = optionalEmployee.get();
        dependent.setEmployee(employee);
        dependentRepository.save(dependent);

        return "Dependente adicionado com sucesso";
    }

    public Long deleteDependent(String cpf) {
        // Busca o dependente pelo CPF
        Dependent dependent = dependentRepository.findByCpf(cpf);
        
        if (dependent == null) {
            throw new RuntimeException("Dependente não encontrado!");
        }

        // Obtém o ID do funcionário antes de deletar o dependente
        Long employeeId = dependent.getEmployee().getId();

        // Exclui o dependente
        dependentRepository.delete(dependent);

        // Retorna o ID do funcionário para redirecionamento
        return employeeId;
    }

    public Iterable<Dependent> findDependentsByEmployee(Employee employee) {
        return dependentRepository.findByEmployee(employee);
    }
}
