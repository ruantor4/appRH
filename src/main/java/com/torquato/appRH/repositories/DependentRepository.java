package com.torquato.appRH.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;

/**
 * Repositório para operações de persistência da entidade {@link Dependent}.
 * 
 * <p>
 * Esta interface estende {@link CrudRepository}, fornecendo métodos
 * para manipulação dos dependentes no banco de dados.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Repository
public interface DependentRepository extends CrudRepository<Dependent, Long> {

	/**
	 * Retorna todos os dependentes associados a um funcionário específico.
	 * 
	 * @param employee O funcionário ao qual os dependentes estão vinculados.
	 * @return Uma lista iterável de dependentes do funcionário.
	 */
	Iterable<Dependent> findByEmployee(Employee employee);

	/**
	 * Busca um dependente pelo CPF.
	 * 
	 * @param cpf CPF do dependente.
	 * @return O dependente correspondente ou {@code null} se não encontrado.
	 */
	Dependent findByCpf(String cpf);

	/**
	 * Busca um dependente pelo ID.
	 * 
	 * @param id ID do dependente.
	 * @return O dependente correspondente ou {@code null} se não encontrado.
	 */
	Dependent findById(long id);

}
