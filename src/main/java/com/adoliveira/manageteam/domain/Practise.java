package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Practise.
 */
@Entity
@Table(name = "practise")
public class Practise implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @OneToOne
    @JoinColumn(unique = true)
    private Stade stade;

    @ManyToMany
    @JoinTable(name = "practise_players",
               joinColumns = @JoinColumn(name = "practises_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "players_id", referencedColumnName = "id"))
    private Set<Player> players = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "practise_manages",
               joinColumns = @JoinColumn(name = "practises_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "manages_id", referencedColumnName = "id"))
    private Set<Manager> manages = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public Practise date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Stade getStade() {
        return stade;
    }

    public Practise stade(Stade stade) {
        this.stade = stade;
        return this;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Practise players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Practise addPlayers(Player player) {
        this.players.add(player);
        return this;
    }

    public Practise removePlayers(Player player) {
        this.players.remove(player);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Manager> getManages() {
        return manages;
    }

    public Practise manages(Set<Manager> managers) {
        this.manages = managers;
        return this;
    }

    public Practise addManages(Manager manager) {
        this.manages.add(manager);
        return this;
    }

    public Practise removeManages(Manager manager) {
        this.manages.remove(manager);
        return this;
    }

    public void setManages(Set<Manager> managers) {
        this.manages = managers;
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
        Practise practise = (Practise) o;
        if (practise.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), practise.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Practise{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            "}";
    }
}
