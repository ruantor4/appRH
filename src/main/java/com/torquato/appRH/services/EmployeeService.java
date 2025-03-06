package com.torquato.appRH.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.torquato.appRH.models.Employee;
import com.torquato.appRH.repositories.EmployeeRepository;
import com.torquato.appRH.repositories.DependentRepository;

/**
 * Serviço responsável pela lógica de negócio relacionada aos funcionários.
 * 
 * <p>
 * Esta classe gerencia as operações de persistência dos funcionários, como
 * salvar,
 * buscar, excluir funcionários e verificar CPF duplicado.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Service
public class EmployeeService {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private DependentRepository dependentRepository;

	/**
	 * Salva um novo funcionário no banco de dados.
	 * 
	 * @param employee O funcionário a ser salvo.
	 */
	public void saveEmployee(Employee employee) {
		employeeRepository.save(employee);
	}

	/**
	 * Retorna todos os funcionários cadastrados no banco de dados.
	 * 
	 * @return Uma lista iterável de todos os funcionários.
	 */
	public Iterable<Employee> getAllEmployees() {
		return employeeRepository.findAll();
	}

	/**
	 * Busca um funcionário pelo ID.
	 * 
	 * @param id O ID do funcionário.
	 * @return O funcionário correspondente ou {@code null} se não encontrado.
	 */
	public Employee getEmployeeById(long id) {
		return employeeRepository.findById(id);
	}

	/**
	 * Deleta um funcionário com base no ID fornecido.
	 * 
	 * @param id O ID do funcionário a ser deletado.
	 */
	public void deleteEmployee(long id) {
		Employee employee = employeeRepository.findById(id);
		if (employee != null) {
			employeeRepository.delete(employee);
		}
	}

	/**
	 * Verifica se o CPF informado já está cadastrado como dependente de algum
	 * funcionário.
	 * 
	 * @param cpf O CPF a ser verificado.
	 * @return {@code true} se o CPF estiver cadastrado como dependente,
	 *         {@code false} caso contrário.
	 */
	public boolean isCpfDuplicated(String cpf) {
		return dependentRepository.findByCpf(cpf) != null;
	}
}
