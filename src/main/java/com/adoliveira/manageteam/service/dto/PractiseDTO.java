package com.adoliveira.manageteam.service.dto;

import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A DTO for the Practise entity.
 */
public class PractiseDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime date;

    private Long stadeId;

    private Set<PlayerDTO> players = new HashSet<>();

    private Set<ManagerDTO> manages = new HashSet<>();

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

    public Long getStadeId() {
        return stadeId;
    }

    public void setStadeId(Long stadeId) {
        this.stadeId = stadeId;
    }

    public Set<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(Set<PlayerDTO> players) {
        this.players = players;
    }

    public Set<ManagerDTO> getManages() {
        return manages;
    }

    public void setManages(Set<ManagerDTO> managers) {
        this.manages = managers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PractiseDTO practiseDTO = (PractiseDTO) o;
        if (practiseDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), practiseDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PractiseDTO{" +
            "id=" + getId() +
            ", date='" + getDate() + "'" +
            ", stade=" + getStadeId() +
            "}";
    }
}
