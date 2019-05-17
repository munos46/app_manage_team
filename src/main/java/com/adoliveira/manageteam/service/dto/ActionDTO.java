package com.adoliveira.manageteam.service.dto;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import com.adoliveira.manageteam.domain.enumeration.TypeAction;

/**
 * A DTO for the Action entity.
 */
public class ActionDTO implements Serializable {

    private Long id;

    @NotNull
    private TypeAction typeAction;

    @NotNull
    private Double minute;

    private Boolean prolongation;

    private String commntary;

    private Long playerOneId;

    private Long playerTwoId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeAction getTypeAction() {
        return typeAction;
    }

    public void setTypeAction(TypeAction typeAction) {
        this.typeAction = typeAction;
    }

    public Double getMinute() {
        return minute;
    }

    public void setMinute(Double minute) {
        this.minute = minute;
    }

    public Boolean isProlongation() {
        return prolongation;
    }

    public void setProlongation(Boolean prolongation) {
        this.prolongation = prolongation;
    }

    public String getCommntary() {
        return commntary;
    }

    public void setCommntary(String commntary) {
        this.commntary = commntary;
    }

    public Long getPlayerOneId() {
        return playerOneId;
    }

    public void setPlayerOneId(Long playerId) {
        this.playerOneId = playerId;
    }

    public Long getPlayerTwoId() {
        return playerTwoId;
    }

    public void setPlayerTwoId(Long playerId) {
        this.playerTwoId = playerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ActionDTO actionDTO = (ActionDTO) o;
        if (actionDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), actionDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ActionDTO{" +
            "id=" + getId() +
            ", typeAction='" + getTypeAction() + "'" +
            ", minute=" + getMinute() +
            ", prolongation='" + isProlongation() + "'" +
            ", commntary='" + getCommntary() + "'" +
            ", playerOne=" + getPlayerOneId() +
            ", playerTwo=" + getPlayerTwoId() +
            "}";
    }
}
