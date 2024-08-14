package com.torquato.appRH.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.torquato.appRH.models.Employee;

@Repository
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	Employee findById(long id);

	Employee findByName(String name);
}
