package com.adoliveira.manageteam.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Championnat entity.
 */
public class ChampionnatDTO implements Serializable {

    private Long id;

    @NotNull
    private String nom;

    private Integer pointVictoire;

    private Integer pointNull;

    private Integer pointDefaite;

    private Integer pointForfait;

    private Long saisonId;

    private Set<TeamDTO> adversaires = new HashSet<>();

    private Set<GameDTO> journees = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getPointVictoire() {
        return pointVictoire;
    }

    public void setPointVictoire(Integer pointVictoire) {
        this.pointVictoire = pointVictoire;
    }

    public Integer getPointNull() {
        return pointNull;
    }

    public void setPointNull(Integer pointNull) {
        this.pointNull = pointNull;
    }

    public Integer getPointDefaite() {
        return pointDefaite;
    }

    public void setPointDefaite(Integer pointDefaite) {
        this.pointDefaite = pointDefaite;
    }

    public Integer getPointForfait() {
        return pointForfait;
    }

    public void setPointForfait(Integer pointForfait) {
        this.pointForfait = pointForfait;
    }

    public Long getSaisonId() {
        return saisonId;
    }

    public void setSaisonId(Long saisonId) {
        this.saisonId = saisonId;
    }

    public Set<TeamDTO> getAdversaires() {
        return adversaires;
    }

    public void setAdversaires(Set<TeamDTO> teams) {
        this.adversaires = teams;
    }

    public Set<GameDTO> getJournees() {
        return journees;
    }

    public void setJournees(Set<GameDTO> games) {
        this.journees = games;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChampionnatDTO championnatDTO = (ChampionnatDTO) o;
        if (championnatDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), championnatDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ChampionnatDTO{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", pointVictoire=" + getPointVictoire() +
            ", pointNull=" + getPointNull() +
            ", pointDefaite=" + getPointDefaite() +
            ", pointForfait=" + getPointForfait() +
            ", saison=" + getSaisonId() +
            "}";
    }
}
