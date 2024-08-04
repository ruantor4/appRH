package com.torquato.appRH.models;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table
public class Candidate implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(unique = true)
	private String rg;

	private String name;

	@Column(unique = true)
	private String email;

	@ManyToOne
	private Vacancy vacancy;

	public Candidate() {

	}

	public Candidate(Long id, String rg, String name, String email, Vacancy vacancy) {
		super();
		this.id = id;
		this.rg = rg;
		this.name = name;
		this.email = email;
		this.vacancy = vacancy;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Vacancy getVacancy() {
		return vacancy;
	}

	public void setVacancy(Vacancy vacancy) {
		this.vacancy = vacancy;
	}

}
