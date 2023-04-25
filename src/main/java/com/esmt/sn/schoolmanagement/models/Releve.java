package com.esmt.sn.schoolmanagement.models;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity(name = "t_releve")
public class Releve {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="releve_id")
	private int id;
	
	@OneToMany(mappedBy = "releve", cascade = CascadeType.PERSIST)
    private List<Note> notes = new ArrayList<>();
	
	@ManyToOne
	private Matiere matiere = null ;
	
	@ManyToOne
	private Bulletin bulletin = null ;

}
