package com.esmt.sn.schoolmanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity(name="t_paiement")
public class Paiement {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="paiement_id")
	private int id;
	
	@ManyToOne
	private Rubrique rubrique = null;
	
	@ManyToOne
	private Eleve eleve = null;
	
	@ManyToOne
	private Caissier caissier = null;
}
