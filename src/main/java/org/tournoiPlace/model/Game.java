package org.tournoiPlace.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "Game")
public class Game implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private Double difficulte;

    @Column(name = "duree_moyenne_match", nullable = false)
    private Double dureeMoyenneMatch;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public Double getDifficulte() {
        return difficulte;
    }
    public void setDifficulte(Double difficulte) {
        this.difficulte = difficulte;
    }
    public Double getDureeMoyenneMatch() {
        return dureeMoyenneMatch;
    }
    public void setDureeMoyenneMatch(Double dureeMoyenneMatch) {
        this.dureeMoyenneMatch = dureeMoyenneMatch;
    }

}
