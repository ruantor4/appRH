package com.torquato.appRH.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Dependent;
import com.torquato.appRH.models.Employee;

@Repository
public interface DependentRepository extends CrudRepository<Dependent, Long> {

	Iterable<Dependent> findByEmployee(Employee employee);

	Dependent findByCpf(String cpf);

	Dependent findById(long id);

}
