package com.torquato.appRH.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa uma vaga de emprego no sistema de RH.
 * 
 * <p>
 * Cada vaga possui um código, nome, descrição, data de publicação e salário,
 * além de uma lista de candidatos associados.
 * </p>
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
public class Vacancy {

	/**
	 * Código identificador único da vaga.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long code;

	/**
	 * Nome da vaga.
	 */
	private String name;

	/**
	 * Descrição detalhada da vaga.
	 */
	private String description;

	/**
	 * Data de publicação da vaga.
	 */
	private String date;

	/**
	 * Faixa salarial da vaga.
	 */
	private String salary;

	/**
	 * Lista de candidatos que se inscreveram para a vaga.
	 * O mapeamento é feito pelo atributo `vacancy` na classe {@link Candidate}.
	 * <p>
	 * Caso a vaga seja removida, todos os candidatos associados também serão
	 * excluídos
	 * devido à estratégia {@code CascadeType.REMOVE}.
	 * </p>
	 */
	@OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE)
	private List<Candidate> candidates;
}
