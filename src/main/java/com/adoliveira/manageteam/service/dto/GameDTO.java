package com.adoliveira.manageteam.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Game entity.
 */
public class GameDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    private Boolean home;

    private Integer myGoal;

    private Integer herGoal;

    private Long teamId;

    private Long secondTeamId;

    private Long stadeId;

    private Set<ManagerDTO> manages = new HashSet<>();

    private Set<PlayerDTO> players = new HashSet<>();

    private Set<ActionDTO> actions = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getDate() {
        return date;
    }

    public void setDate(ZonedDateTime date) {
        this.date = date;
    }

    public Boolean isHome() {
        return home;
    }

    public void setHome(Boolean home) {
        this.home = home;
    }

    public Integer getMyGoal() {
        return myGoal;
    }

    public void setMyGoal(Integer myGoal) {
        this.myGoal = myGoal;
    }

    public Integer getHerGoal() {
        return herGoal;
    }

    public void setHerGoal(Integer herGoal) {
        this.herGoal = herGoal;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Long getSecondTeamId() {
        return secondTeamId;
    }

    public void setSecondTeamId(Long teamId) {
        this.secondTeamId = teamId;
    }

    public Long getStadeId() {
        return stadeId;
    }

    public void setStadeId(Long stadeId) {
        this.stadeId = stadeId;
    }

    public Set<ManagerDTO> getManages() {
        return manages;
    }

    public void setManages(Set<ManagerDTO> managers) {
        this.manages = managers;
    }

    public Set<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerDTO> players) {
        this.players = players;
    }

    public Set<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(Set<ActionDTO> actions) {
        this.actions = actions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GameDTO gameDTO = (GameDTO) o;
        if (gameDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), gameDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "GameDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", home='" + isHome() + "'" +
            ", myGoal=" + getMyGoal() +
            ", herGoal=" + getHerGoal() +
            ", team=" + getTeamId() +
            ", secondTeam=" + getSecondTeamId() +
            ", stade=" + getStadeId() +
            "}";
    }
}
