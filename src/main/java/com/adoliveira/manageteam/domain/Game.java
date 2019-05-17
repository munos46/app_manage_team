package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Game.
 */
@Entity
@Table(name = "game")
public class Game implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_date", nullable = false)
    private ZonedDateTime date;

    @Column(name = "home")
    private Boolean home;

    @Column(name = "my_goal")
    private Integer myGoal;

    @Column(name = "her_goal")
    private Integer herGoal;

    @OneToOne
    @JoinColumn(unique = true)
    private Team team;

    @OneToOne
    @JoinColumn(unique = true)
    private Team secondTeam;

    @OneToOne
    @JoinColumn(unique = true)
    private Stade stade;

    @ManyToMany
    @JoinTable(name = "game_manages",
               joinColumns = @JoinColumn(name = "games_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "manages_id", referencedColumnName = "id"))
    private Set<Manager> manages = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "game_players",
               joinColumns = @JoinColumn(name = "games_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "players_id", referencedColumnName = "id"))
    private Set<Player> players = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "game_actions",
               joinColumns = @JoinColumn(name = "games_id", referencedColumnName = "id"),
               inverseJoinColumns = @JoinColumn(name = "actions_id", referencedColumnName = "id"))
    private Set<Action> actions = new HashSet<>();

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

    public Game date(ZonedDateTime date) {
        this.date = date;
        return this;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Boolean isHome() {
        return home;
    }

    public Game home(Boolean home) {
        this.home = home;
        return this;
    }

    public void setHome(Boolean home) {
        this.home = home;
    }

    public Integer getMyGoal() {
        return myGoal;
    }

    public Game myGoal(Integer myGoal) {
        this.myGoal = myGoal;
        return this;
    }

    public void setMyGoal(Integer myGoal) {
        this.myGoal = myGoal;
    }

    public Integer getHerGoal() {
        return herGoal;
    }

    public Game herGoal(Integer herGoal) {
        this.herGoal = herGoal;
        return this;
    }

    public void setHerGoal(Integer herGoal) {
        this.herGoal = herGoal;
    }

    public Team getTeam() {
        return team;
    }

    public Game team(Team team) {
        this.team = team;
        return this;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Team getSecondTeam() {
        return secondTeam;
    }

    public Game secondTeam(Team team) {
        this.secondTeam = team;
        return this;
    }

    public void setSecondTeam(Team team) {
        this.secondTeam = team;
    }

    public Stade getStade() {
        return stade;
    }

    public Game stade(Stade stade) {
        this.stade = stade;
        return this;
    }

    public void setStade(Stade stade) {
        this.stade = stade;
    }

    public Set<Manager> getManages() {
        return manages;
    }

    public Game manages(Set<Manager> managers) {
        this.manages = managers;
        return this;
    }

    public Game addManages(Manager manager) {
        this.manages.add(manager);
        return this;
    }

    public Game removeManages(Manager manager) {
        this.manages.remove(manager);
        return this;
    }

    public void setManages(Set<Manager> managers) {
        this.manages = managers;
    }

    public Set<Player> getPlayers() {
        return players;
    }

    public Game players(Set<Player> players) {
        this.players = players;
        return this;
    }

    public Game addPlayers(Player player) {
        this.players.add(player);
        return this;
    }

    public Game removePlayers(Player player) {
        this.players.remove(player);
        return this;
    }

    public void setPlayers(Set<Player> players) {
        this.players = players;
    }

    public Set<Action> getActions() {
        return actions;
    }

    public Game actions(Set<Action> actions) {
        this.actions = actions;
        return this;
    }

    public Game addActions(Action action) {
        this.actions.add(action);
        return this;
    }

    public Game removeActions(Action action) {
        this.actions.remove(action);
        return this;
    }

    public void setActions(Set<Action> actions) {
        this.actions = actions;
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
        Game game = (Game) o;
        if (game.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), game.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Game{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", home='" + isHome() + "'" +
            ", myGoal=" + getMyGoal() +
            ", herGoal=" + getHerGoal() +
            "}";
    }
}
