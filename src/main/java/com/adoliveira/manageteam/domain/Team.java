package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Team.
 */
@Entity
@Table(name = "team")
public class Team implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "adversaire")
    private Boolean adversaire;

    @Lob
    @Column(name = "logo")
    private byte[] logo;

    @Column(name = "logo_content_type")
    private String logoContentType;

    @ManyToMany
    @JoinTable(name = "team_managers",
               joinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "managers_id", referencedColumnName = "id"))
    private Set<Manager> managers = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "team_players",
               joinColumns = @JoinColumn(name = "teams_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "players_id", referencedColumnName = "id"))
    private Set<Player> players = new HashSet<>();

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

    public Team nom(String nom) {
        this.nom = nom;
        return this;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Boolean isAdversaire() {
        return adversaire;
    }

    public Team adversaire(Boolean adversaire) {
        this.adversaire = adversaire;
        return this;
    }

    public void setAdversaire(Boolean adversaire) {
        this.adversaire = adversaire;
    }

    public byte[] getLogo() {
        return logo;
    }

    public Team logo(byte[] logo) {
        this.logo = logo;
        return this;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoContentType() {
        return logoContentType;
    }

    public Team logoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
        return this;
    }

    public void setLogoContentType(String logoContentType) {
        this.logoContentType = logoContentType;
    }

    public Set<Manager> getManagers() {
        return managers;
    }

    public Team managers(Set<Manager> managers) {
        this.managers = managers;
        return this;
    }

    public Team addManagers(Manager manager) {
        this.managers.add(manager);
        return this;
    }

    public Team removeManagers(Manager manager) {
        this.managers.remove(manager);
        return this;
    }

    public void setManagers(Set<Manager> managers) {
        this.managers = managers;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Team players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Team addPlayers(Player player) {
        this.players.add(player);
        return this;
    }

    public Team removePlayers(Player player) {
        this.players.remove(player);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
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
        Team team = (Team) o;
        if (team.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), team.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Team{" +
            "id=" + getId() +
            ", nom='" + getNom() + "'" +
            ", adversaire='" + isAdversaire() + "'" +
            ", logo='" + getLogo() + "'" +
            ", logoContentType='" + getLogoContentType() + "'" +
            "}";
    }
}
