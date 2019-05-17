package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Player.
 */
@Entity
@Table(name = "player")
public class Player implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "hire_date")
    private LocalDate hireDate;

    @Column(name = "annee_arrivee")
    private LocalDate anneeArrivee;

    @Column(name = "poids")
    private Double poids;

    @Column(name = "taille")
    private Integer taille;

    @Column(name = "num_maillot")
    private Integer numMaillot;

    @Column(name = "num_licence")
    private String numLicence;

    @Lob
    @Column(name = "img_profile")
    private byte[] imgProfile;

    @Column(name = "img_profile_content_type")
    private String imgProfileContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User player;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public Player firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Player lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Player email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Player phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Player hireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getAnneeArrivee() {
        return anneeArrivee;
    }

    public Player anneeArrivee(LocalDate anneeArrivee) {
        this.anneeArrivee = anneeArrivee;
        return this;
    }

    public void setAnneeArrivee(LocalDate anneeArrivee) {
        this.anneeArrivee = anneeArrivee;
    }

    public Double getPoids() {
        return poids;
    }

    public Player poids(Double poids) {
        this.poids = poids;
        return this;
    }

    public void setPoids(Double poids) {
        this.poids = poids;
    }

    public Integer getTaille() {
        return taille;
    }

    public Player taille(Integer taille) {
        this.taille = taille;
        return this;
    }

    public void setTaille(Integer taille) {
        this.taille = taille;
    }

    public Integer getNumMaillot() {
        return numMaillot;
    }

    public Player numMaillot(Integer numMaillot) {
        this.numMaillot = numMaillot;
        return this;
    }

    public void setNumMaillot(Integer numMaillot) {
        this.numMaillot = numMaillot;
    }

    public String getNumLicence() {
        return numLicence;
    }

    public Player numLicence(String numLicence) {
        this.numLicence = numLicence;
        return this;
    }

    public void setNumLicence(String numLicence) {
        this.numLicence = numLicence;
    }

    public byte[] getImgProfile() {
        return imgProfile;
    }

    public Player imgProfile(byte[] imgProfile) {
        this.imgProfile = imgProfile;
        return this;
    }

    public void setImgProfile(byte[] imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getImgProfileContentType() {
        return imgProfileContentType;
    }

    public Player imgProfileContentType(String imgProfileContentType) {
        this.imgProfileContentType = imgProfileContentType;
        return this;
    }

    public void setImgProfileContentType(String imgProfileContentType) {
        this.imgProfileContentType = imgProfileContentType;
    }

    public User getPlayer() {
        return player;
    }

    public Player player(User user) {
        this.player = user;
        return this;
    }

    public void setPlayer(User user) {
        this.player = user;
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
        Player player = (Player) o;
        if (player.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Player{" +
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
            ", imgProfileContentType='" + getImgProfileContentType() + "'" +
            "}";
    }
}
