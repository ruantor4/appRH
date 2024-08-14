package com.torquato.appRH.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Dependent implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	private Long id;
	private String name;

	@Column(unique = true)
	private String cpf;
	private String birthday;

	@ManyToOne
	private Employee employee;

	public Dependent() {

	}

	public Dependent(Long id, String name, String cpf, String birthday, Employee employee) {
		super();
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.birthday = birthday;
		this.employee = employee;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

}