package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

import com.adoliveira.manageteam.domain.enumeration.TypeAction;

/**
 * A Action.
 */
@Entity
@Table(name = "action")
public class Action implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "type_action", nullable = false)
    private TypeAction typeAction;

    @NotNull
    @Column(name = "minute", nullable = false)
    private Double minute;

    @Column(name = "prolongation")
    private Boolean prolongation;

    @Column(name = "commntary")
    private String commntary;

    @OneToOne
    @JoinColumn(unique = true)
    private Player playerOne;

    @OneToOne
    @JoinColumn(unique = true)
    private Player playerTwo;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TypeAction getTypeAction() {
        return typeAction;
    }

    public Action typeAction(TypeAction typeAction) {
        this.typeAction = typeAction;
        return this;
    }

    public void setTypeAction(TypeAction typeAction) {
        this.typeAction = typeAction;
    }

    public Double getMinute() {
        return minute;
    }

    public Action minute(Double minute) {
        this.minute = minute;
        return this;
    }

    public void setMinute(Double minute) {
        this.minute = minute;
    }

    public Boolean isProlongation() {
        return prolongation;
    }

    public Action prolongation(Boolean prolongation) {
        this.prolongation = prolongation;
        return this;
    }

    public void setProlongation(Boolean prolongation) {
        this.prolongation = prolongation;
    }

    public String getCommntary() {
        return commntary;
    }

    public Action commntary(String commntary) {
        this.commntary = commntary;
        return this;
    }

    public void setCommntary(String commntary) {
        this.commntary = commntary;
    }

    public Player getPlayerOne() {
        return playerOne;
    }

    public Action playerOne(Player player) {
        this.playerOne = player;
        return this;
    }

    public void setPlayerOne(Player player) {
        this.playerOne = player;
    }

    public Player getPlayerTwo() {
        return playerTwo;
    }

    public Action playerTwo(Player player) {
        this.playerTwo = player;
        return this;
    }

    public void setPlayerTwo(Player player) {
        this.playerTwo = player;
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
        Action action = (Action) o;
        if (action.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), action.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Action{" +
            "id=" + getId() +
            ", typeAction='" + getTypeAction() + "'" +
            ", minute=" + getMinute() +
            ", prolongation='" + isProlongation() + "'" +
            ", commntary='" + getCommntary() + "'" +
            "}";
    }
}
