package com.selyuto.termbase.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.selyuto.termbase.enums.Status;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(generator = "user_seq", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "email")
    private String email;
    @Column(name = "password", updatable = false)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;
    @CreatedDate
    @Column(name = "date_created", updatable = false)
    private Date dateCreated;
    @LastModifiedDate
    @Column(name = "date_last_modified")
    private Date dateUpdated;
    @ManyToMany
    @JoinTable(
            name = "users_to_privileges",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id"))
    private Set<Privilege> privileges;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @JsonIgnore
    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    @JsonIgnore
    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Set<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(Set<Privilege> privileges) {
        this.privileges = privileges;
    }

    @JsonIgnore
    public String getFullName() {
        return getFirstName() + " " + getLastName();
    }
}
