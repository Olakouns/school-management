package com.esmt.sn.schoolmanagement.models;


import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Caissier extends Utilisateur{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_caissier")
    protected int id;

	@OneToMany(mappedBy = "caissier",cascade = {CascadeType.PERSIST})
	protected List<Paiement> paiements = new ArrayList<>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public List<Paiement> getPaiements() {
		return paiements;
	}

	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}

	
}
