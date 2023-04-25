package com.esmt.sn.schoolmanagement.models;

import com.esmt.sn.schoolmanagement.models.enums.SectionType;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "section_id")
    private Long section_id;

    @Enumerated(EnumType.STRING)
    private SectionType sectionType;

    @OneToMany(mappedBy = "section", cascade = CascadeType.PERSIST)
    private List<Classe> classes = new ArrayList<>();

    @OneToMany(mappedBy = "section", cascade = CascadeType.PERSIST)
    private List<Rubrique> rubriques = new ArrayList<>();

    private String nom;

    public Section(String nom) {
        super();
        this.nom = nom;
    }

    public Section() {

    }

    public SectionType getSectionType() {
        return sectionType;
    }

    public void setSectionType(SectionType sectionType) {
        this.sectionType = sectionType;
    }

    public Long getSection_id() {
        return section_id;
    }


    public void setSection_id(Long section_id) {
        this.section_id = section_id;
    }


    public String getNom() {
        return nom;
    }


    public void setNom(String nom) {
        this.nom = nom;
    }


    public List<Classe> getClasses() {
        return classes;
    }


    public void setClasses(List<Classe> classes) {
        this.classes = classes;
    }


    public List<Rubrique> getRubriques() {
        return rubriques;
    }


    public void setRubriques(List<Rubrique> rubriques) {
        this.rubriques = rubriques;
    }


}
