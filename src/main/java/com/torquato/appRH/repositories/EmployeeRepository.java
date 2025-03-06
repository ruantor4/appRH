package com.torquato.appRH.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Employee;

/**
 * Repositório para operações de persistência da entidade {@link Employee}.
 * 
 * <p>
 * Esta interface estende {@link CrudRepository}, fornecendo métodos
 * para manipulação dos funcionários no banco de dados.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	/**
	 * Busca um funcionário pelo ID.
	 * 
	 * @param id ID do funcionário.
	 * @return O funcionário correspondente ou {@code null} se não encontrado.
	 */
	Employee findById(long id);

	/**
	 * Busca um funcionário pelo nome.
	 * 
	 * @param name Nome do funcionário.
	 * @return O funcionário correspondente ou {@code null} se não encontrado.
	 */
	Employee findByName(String name);

}
