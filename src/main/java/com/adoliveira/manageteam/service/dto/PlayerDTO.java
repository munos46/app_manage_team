package com.adoliveira.manageteam.service.dto;

import java.time.LocalDate;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Lob;

/**
 * A DTO for the Player entity.
 */
public class PlayerDTO implements Serializable {

    private Long id;

    @NotNull
    private String firstName;

    @NotNull
    private String lastName;

    private String email;

    private String phoneNumber;

    private LocalDate hireDate;

    private LocalDate anneeArrivee;

    private Double poids;

    private Integer taille;

    private Integer numMaillot;

    private String numLicence;

    @Lob
    private byte[] imgProfile;
    private String imgProfileContentType;

    private Long playerId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getAnneeArrivee() {
        return anneeArrivee;
    }

    public void setAnneeArrivee(LocalDate anneeArrivee) {
        this.anneeArrivee = anneeArrivee;
    }

    public Double getPoids() {
        return poids;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public Integer getNumMaillot() {
        return numMaillot;
    }

    public void setNumMaillot(Integer numMaillot) {
        this.numMaillot = numMaillot;
    }

    public String getNumLicence() {
        return numLicence;
    }

    public void setNumLicence(String numLicence) {
        this.numLicence = numLicence;
    }

    public byte[] getImgProfile() {
        return imgProfile;
    }

    public void setImgProfile(byte[] imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getImgProfileContentType() {
        return imgProfileContentType;
    }

    public void setImgProfileContentType(String imgProfileContentType) {
        this.imgProfileContentType = imgProfileContentType;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long userId) {
        this.playerId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PlayerDTO playerDTO = (PlayerDTO) o;
        if (playerDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), playerDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "PlayerDTO{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", anneeArrivee='" + getAnneeArrivee() + "'" +
            ", poids=" + getPoids() +
            ", taille=" + getTaille() +
            ", numMaillot=" + getNumMaillot() +
            ", numLicence='" + getNumLicence() + "'" +
            ", imgProfile='" + getImgProfile() + "'" +
            ", player=" + getPlayerId() +
            "}";
    }
}
