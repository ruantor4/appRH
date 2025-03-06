package com.torquato.appRH.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um funcionário no sistema de RH.
 * 
 * <p>
 * Cada funcionário possui um nome, data de admissão e e-mail, além de uma lista
 * de dependentes associados.
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
public class Employee {

	/**
	 * Identificador único do funcionário.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	/**
	 * Nome completo do funcionário.
	 */
	private String name;

	/**
	 * Data de admissão do funcionário.
	 */
	private String date;

	/**
	 * Endereço de e-mail do funcionário.
	 */
	private String email;

	/**
	 * Lista de dependentes vinculados ao funcionário.
	 * O mapeamento é feito pelo atributo `employee` na classe {@link Dependent}.
	 * <p>
	 * Caso um funcionário seja removido, todos os seus dependentes também serão
	 * excluídos
	 * devido à estratégia {@code CascadeType.REMOVE}.
	 * </p>
	 */
	@OneToMany(mappedBy = "employee", cascade = CascadeType.REMOVE)
	private List<Dependent> dependents;
}
