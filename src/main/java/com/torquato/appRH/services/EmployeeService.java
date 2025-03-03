package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torquato.appRH.models.Employee;
import com.torquato.appRH.repositories.EmployeeRepository;
import com.torquato.appRH.repositories.DependentRepository;

@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DependentRepository dependentRepository;

	// SALVAR FUNCIONÁRIO
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	// OBTER TODOS OS FUNCIONÁRIOS
	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	// OBTER FUNCIONÁRIO POR ID
	public Employee getEmployeeById(long id) {
		return employeeRepository.findById(id);
	}

	// DELETAR FUNCIONÁRIO
	public void deleteEmployee(long id) {
		Employee employee = employeeRepository.findById(id);
		if (employee != null) {
			employeeRepository.delete(employee);
		}
	}

	

	// VERIFICAR CPF DUPLICADO
	public boolean isCpfDuplicated(String cpf) {
		return dependentRepository.findByCpf(cpf) != null;
	}
}
