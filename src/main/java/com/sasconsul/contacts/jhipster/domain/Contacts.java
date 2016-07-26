package com.sasconsul.contacts.jhipster.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Contacts.
 */
@Entity
@Table(name = "contacts")
public class Contacts implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "profession")
    private String profession;

    @Column(name = "employed")
    private Boolean employed;

    @Column(name = "created_on")
    private ZonedDateTime createdOn = ZonedDateTime.now(ZoneId.of("UTC"));

    @Column(name = "modified_on")
    private ZonedDateTime modifiedOn = ZonedDateTime.now(ZoneId.of("UTC"));;

    @Column(name = "deleted", columnDefinition="Boolean default 'false'")
    private Boolean deleted = false;

    @Column(name = "employee_id")
    private Long employeeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public Boolean isEmployed() {
        return employed;
    }

    public void setEmployed(Boolean employed) {
        this.employed = employed;
    }

    public ZonedDateTime getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(ZonedDateTime createdOn) {
        this.createdOn = createdOn;
    }

    public ZonedDateTime getModifiedOn() {
        return modifiedOn;
    }

    public void setModifiedOn(ZonedDateTime modifiedOn) {
        this.modifiedOn = modifiedOn;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Contacts contacts = (Contacts) o;
        if(contacts.id == null || id == null) {
            return false;
        }
        return Objects.equals(id, contacts.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Contacts{" +
            "id=" + id +
            ", name='" + name + "'" +
            ", email='" + email + "'" +
            ", profession='" + profession + "'" +
            ", employed='" + employed + "'" +
            ", createdOn='" + createdOn + "'" +
            ", modifiedOn='" + modifiedOn + "'" +
            ", deleted='" + deleted + "'" +
            ", employeeId='" + employeeId + "'" +
            '}';
    }
}
