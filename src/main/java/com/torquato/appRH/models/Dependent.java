package com.torquato.appRH.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dependent {

	@Id
	@GeneratedValue
	private Long id;

	private String name;

	@Column(unique = true)
	private String cpf;

	private String birthday;

	@ManyToOne
	private Employee employee;

}