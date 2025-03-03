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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Vacancy {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long code;

	private String name;

	private String description;

	private String date;

	private String salary;

	@OneToMany(mappedBy = "vacancy", cascade = CascadeType.REMOVE)
	private List<Candidate> candidates;

}
