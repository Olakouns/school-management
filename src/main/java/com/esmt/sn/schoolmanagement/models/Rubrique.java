package com.esmt.sn.schoolmanagement.models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity(name = "t_rubrique")
public class Rubrique {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	@Column(name="rubrique_id")
	private int id;

	private String label;

	private BigDecimal amount;
	
	@ManyToOne
	private Section section;

	@ManyToOne
	private Classe classe;
	
	@OneToMany(mappedBy = "rubrique", cascade = CascadeType.PERSIST)
    private List<Exoneration> exonerations = new ArrayList<>();

    @OneToMany(mappedBy = "rubrique", cascade = CascadeType.PERSIST)
    private List<Paiement> paiements = new ArrayList<>();

	public Rubrique() {
	}

	public Rubrique(String label, BigDecimal amount) {
		this.label = label;
		this.amount = amount;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Section getSection() {
		return section;
	}

	public void setSection(Section section) {
		this.section = section;
	}

	public Classe getClasse() {
		return classe;
	}

	public void setClasse(Classe classe) {
		this.classe = classe;
	}

	public List<Exoneration> getExonerations() {
		return exonerations;
	}

	public void setExonerations(List<Exoneration> exonerations) {
		this.exonerations = exonerations;
	}

	public List<Paiement> getPaiements() {
		return paiements;
	}

	public void setPaiements(List<Paiement> paiements) {
		this.paiements = paiements;
	}

	@Override
	public String toString() {
		return "Rubrique{" +
				"label='" + label + '\'' +
				", amount=" + amount +
				", classe=" + classe.getName() +
				'}';
	}


}
