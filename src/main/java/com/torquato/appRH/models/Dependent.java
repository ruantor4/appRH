package com.torquato.appRH.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Representa um dependente de um {@link Employee} no sistema de RH.
 * 
 * <p>
 * Cada dependente está vinculado a um funcionário e possui informações
 * como nome, CPF e data de nascimento.
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
public class Dependent {

    /**
     * Identificador único do dependente.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Nome completo do dependente.
     */
    private String name;

    /**
     * CPF do dependente. Deve ser único no sistema.
     */
    @Column(unique = true)
    private String cpf;

    /**
     * Data de nascimento do dependente.
     */
    private String birthday;

    /**
     * Funcionário ao qual o dependente está vinculado.
     */
    @ManyToOne
    private Employee employee;
}
