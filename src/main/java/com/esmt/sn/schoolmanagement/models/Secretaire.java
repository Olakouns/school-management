package com.esmt.sn.schoolmanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Secretaire extends Utilisateur{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_secretaire")
    protected int id;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

    
}
