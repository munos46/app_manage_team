package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Championnat.
 */
@Entity
@Table(name = "championnat")
public class Championnat implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "point_victoire")
    private Integer pointVictoire;

    @Column(name = "point_null")
    private Integer pointNull;

    @Column(name = "point_defaite")
    private Integer pointDefaite;

    @Column(name = "point_forfait")
    private Integer pointForfait;

    @OneToOne
    @JoinColumn(unique = true)
    private Saison saison;

    @ManyToMany
    @JoinTable(name = "championnat_adversaires",
               joinColumns = @JoinColumn(name = "championnats_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "adversaires_id", referencedColumnName = "id"))
    private Set<Team> adversaires = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "championnat_journees",
               joinColumns = @JoinColumn(name = "championnats_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "journees_id", referencedColumnName = "id"))
    private Set<Game> journees = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public Championnat nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPointVictoire() {
        return pointVictoire;
    }

    public Championnat pointVictoire(Integer pointVictoire) {
        this.pointVictoire = pointVictoire;
        return this;
    }

    public void setPointVictoire(Integer pointVictoire) {
        this.pointVictoire = pointVictoire;
    }

    public Integer getPointNull() {
        return pointNull;
    }

    public Championnat pointNull(Integer pointNull) {
        this.pointNull = pointNull;
        return this;
    }

    public void setPointNull(Integer pointNull) {
        this.pointNull = pointNull;
    }

    public Integer getPointDefaite() {
        return pointDefaite;
    }

    public Championnat pointDefaite(Integer pointDefaite) {
        this.pointDefaite = pointDefaite;
        return this;
    }

    public void setPointDefaite(Integer pointDefaite) {
        this.pointDefaite = pointDefaite;
    }

    public Integer getPointForfait() {
        return pointForfait;
    }

    public Championnat pointForfait(Integer pointForfait) {
        this.pointForfait = pointForfait;
        return this;
    }

    public void setPointForfait(Integer pointForfait) {
        this.pointForfait = pointForfait;
    }

    public Saison getSaison() {
        return saison;
    }

    public Championnat saison(Saison saison) {
        this.saison = saison;
        return this;
    }

    public void setSaison(Saison saison) {
        this.saison = saison;
    }

    public Set<Team> getAdversaires() {
        return adversaires;
    }

    public Championnat adversaires(Set<Team> teams) {
        this.adversaires = teams;
        return this;
    }

    public Championnat addAdversaires(Team team) {
        this.adversaires.add(team);
        return this;
    }

    public Championnat removeAdversaires(Team team) {
        this.adversaires.remove(team);
        return this;
    }

    public void setAdversaires(Set<Team> teams) {
        this.adversaires = teams;
    }

    public Set<Game> getJournees() {
        return journees;
    }

    public Championnat journees(Set<Game> games) {
        this.journees = games;
        return this;
    }

    public Championnat addJournees(Game game) {
        this.journees.add(game);
        return this;
    }

    public Championnat removeJournees(Game game) {
        this.journees.remove(game);
        return this;
    }

    public void setJournees(Set<Game> games) {
        this.journees = games;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Championnat championnat = (Championnat) o;
        if (championnat.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), championnat.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Championnat{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", pointVictoire=" + getPointVictoire() +
            ", pointNull=" + getPointNull() +
            ", pointDefaite=" + getPointDefaite() +
            ", pointForfait=" + getPointForfait() +
            "}";
    }
}
