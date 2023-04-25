package com.esmt.sn.schoolmanagement.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity(name = "t_matiere")
public class Matiere {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="matiere_id")
	private int id;
}
