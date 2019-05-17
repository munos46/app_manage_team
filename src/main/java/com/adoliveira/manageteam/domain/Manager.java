package com.adoliveira.manageteam.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

/**
 * A Manager.
 */
@Entity
@Table(name = "manager")
public class Manager implements Serializable {

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

    @Lob
    @Column(name = "img_profile")
    private byte[] imgProfile;

    @Column(name = "img_profile_content_type")
    private String imgProfileContentType;

    @OneToOne
    @JoinColumn(unique = true)
    private User manager;

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

    public Manager firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Manager lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public Manager email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public Manager phoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public Manager hireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
        return this;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public LocalDate getAnneeArrivee() {
        return anneeArrivee;
    }

    public Manager anneeArrivee(LocalDate anneeArrivee) {
        this.anneeArrivee = anneeArrivee;
        return this;
    }

    public void setAnneeArrivee(LocalDate anneeArrivee) {
        this.anneeArrivee = anneeArrivee;
    }

    public byte[] getImgProfile() {
        return imgProfile;
    }

    public Manager imgProfile(byte[] imgProfile) {
        this.imgProfile = imgProfile;
        return this;
    }

    public void setImgProfile(byte[] imgProfile) {
        this.imgProfile = imgProfile;
    }

    public String getImgProfileContentType() {
        return imgProfileContentType;
    }

    public Manager imgProfileContentType(String imgProfileContentType) {
        this.imgProfileContentType = imgProfileContentType;
        return this;
    }

    public void setImgProfileContentType(String imgProfileContentType) {
        this.imgProfileContentType = imgProfileContentType;
    }

    public User getManager() {
        return manager;
    }

    public Manager manager(User user) {
        this.manager = user;
        return this;
    }

    public void setManager(User user) {
        this.manager = user;
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
        Manager manager = (Manager) o;
        if (manager.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), manager.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Manager{" +
            "id=" + getId() +
            ", firstName='" + getFirstName() + "'" +
            ", lastName='" + getLastName() + "'" +
            ", email='" + getEmail() + "'" +
            ", phoneNumber='" + getPhoneNumber() + "'" +
            ", hireDate='" + getHireDate() + "'" +
            ", anneeArrivee='" + getAnneeArrivee() + "'" +
            ", imgProfile='" + getImgProfile() + "'" +
            ", imgProfileContentType='" + getImgProfileContentType() + "'" +
            "}";
    }
}
