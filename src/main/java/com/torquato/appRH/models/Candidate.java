package com.torquato.appRH.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um candidato no sistema de recrutamento.
 * Cada candidato está associado a uma {@link Vacancy}.
 * 
 * <p>
 * Esta classe utiliza Lombok para gerar automaticamente métodos como
 * getters, setters, `equals()`, `hashCode()` e `toString()`.
 * </p>
 * 
 * @author Ruan Torquato
 * @version 1.0
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Candidate {

	/**
	 * Identificador único do candidato.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Registro Geral (RG) do candidato. Deve ser único no sistema.
	 */
	@Column(unique = true)
	private String rg;

	/**
	 * Nome completo do candidato.
	 */
	private String name;

	/**
	 * Endereço de e-mail do candidato. Deve ser único no sistema.
	 */
	@Column(unique = true)
	private String email;

	/**
	 * Vaga à qual o candidato está associado.
	 */
	@ManyToOne
	private Vacancy vacancy;
}
