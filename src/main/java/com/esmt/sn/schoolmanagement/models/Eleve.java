package com.esmt.sn.schoolmanagement.models;

import com.esmt.sn.schoolmanagement.models.enums.GenderType;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Eleve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_eleve")
    protected int id;

    private String firstname;
    private String lastname;
    private Date birthday;
    @Enumerated(EnumType.STRING)
    private GenderType genderType;
    private String matrimonial;
    private String personnelNumber;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.PERSIST)
    private List<Bulletin> bulletins;

    @ManyToOne
    private Classe classe;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.PERSIST)
    private List<Paiement> paiements;

    @OneToMany(mappedBy = "eleve", cascade = CascadeType.PERSIST)
    private List<Exoneration> exaunerations;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public GenderType getGenderType() {
        return genderType;
    }

    public void setGenderType(GenderType genderType) {
        this.genderType = genderType;
    }

    public String getMatrimonial() {
        return matrimonial;
    }

    public void setMatrimonial(String matrimonial) {
        this.matrimonial = matrimonial;
    }

    public List<Bulletin> getBulletins() {
        return bulletins;
    }

    public void setBulletins(List<Bulletin> bulletins) {
        this.bulletins = bulletins;
    }

    public Classe getClasse() {
        return classe;
    }

    public void setClasse(Classe classe) {
        this.classe = classe;
    }

    public List<Paiement> getPaiements() {
        return paiements;
    }

    public void setPaiements(List<Paiement> paiements) {
        this.paiements = paiements;
    }

    public List<Exoneration> getExaunerations() {
        return exaunerations;
    }

    public void setExaunerations(List<Exoneration> exaunerations) {
        this.exaunerations = exaunerations;
    }

    public String getPersonnelNumber() {
        return personnelNumber;
    }

    public void setPersonnelNumber(String personnelNumber) {
        this.personnelNumber = personnelNumber;
    }

    @Override
    public String toString() {
        return "Eleve{" +
                "firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                '}';
    }
}
