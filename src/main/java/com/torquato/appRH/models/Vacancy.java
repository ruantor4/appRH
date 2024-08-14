package com.torquato.appRH.models;

import java.io.Serializable;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table
public class Vacancy implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long code;

	private String name;
	private String description;
	private String date;
	private String salary;

	@OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE)
	private List<Candidate> candidates;

	public Vacancy() {

	}

	public Vacancy(Long code, String name, String description, String date, String salary, List<Candidate> candidates) {
		super();
		this.code = code;
		this.name = name;
		this.description = description;
		this.date = date;
		this.salary = salary;
		this.candidates = candidates;
	}

	public Long getCode() {
		return code;
	}

	public void setCode(Long id) {
		this.code = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSalary() {
		return salary;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public List<Candidate> getCandidates() {
		return candidates;
	}

	public void setCandidates(List<Candidate> candidates) {
		this.candidates = candidates;
	}

}
