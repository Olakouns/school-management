package com.esmt.sn.schoolmanagement.models;

import jakarta.persistence.*;

@Entity
public class Bulletin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id_bulletin")
    protected int id;

    @ManyToOne
    private Eleve eleve;
}