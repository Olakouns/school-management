package com.esmt.sn.schoolmanagement.models;


import jakarta.persistence.*;

import java.util.List;

@Entity
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_eleve")
    protected int id;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.PERSIST)
    private List<Bulletin> bulletins;

    @ManyToOne
    private Classe classe;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.PERSIST)
    private  List<Paiement> paiements;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.PERSIST)
    private  List<Exoneration> exaunerations;

}
