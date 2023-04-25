package com.esmt.sn.schoolmanagement.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "t_classe")
public class Classe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "classe_id")
    private Long classe_id;

    private String name;

    @ManyToOne
    private Section section;

    @OneToMany(mappedBy = "classe", cascade = CascadeType.PERSIST)
    private List<Rubrique> rubriques = new ArrayList<>();

    public Classe(String name) {
        this.name = name;
    }

    public Classe() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public List<Rubrique> getRubriques() {
        return rubriques;
    }

    public void setRubriques(List<Rubrique> rubriques) {
        this.rubriques = rubriques;
    }

    @Override
    public String toString() {
        return "Classe{" +
                "name='" + name + '\'' +
                ", rubriques=" + rubriques +
                '}';
    }

    public void addRubrique(Rubrique rubrique) {
        rubrique.setClasse(this);
        this.rubriques.add(rubrique);
    }
}
