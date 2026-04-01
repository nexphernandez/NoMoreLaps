package com.nomorelaps.domain.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Domain model representing an end User.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class User {
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean calendarEnable;
    private LocalDateTime createAt;
    private Role role;
    private Set<Company> companies = new HashSet<>();
    private Set<Reservation> reservations = new HashSet<>();
    private Set<Sanction> sanctions = new HashSet<>();

    /**
     * Empty constructor
     */
    public User() {
    }

    /**
     * Constructor with all parameters
     * 
     * @param id             user unique digital identifier
     * @param name           real or alias user identity
     * @param email          contact or login coordinate
     * @param password       encoded credential hash key
     * @param calendarEnable user interface preferences setting
     * @param createAt       backend auditing tracking stamp
     * @param role           linked basic permissions block
     * @param companies      associated companies for business accounts
     * @param reservations   list of standard user operations
     * @param sanctions      array of punitive applied actions
     */
    public User(Long id, String name, String email, String password, boolean calendarEnable, LocalDateTime createAt,
            Role role, Set<Company> companies, Set<Reservation> reservations, Set<Sanction> sanctions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.calendarEnable = calendarEnable;
        this.createAt = createAt;
        this.role = role;
        this.companies = companies;
        this.reservations = reservations;
        this.sanctions = sanctions;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isCalendarEnable() {
        return calendarEnable;
    }

    public void setCalendarEnable(boolean calendarEnable) {
        this.calendarEnable = calendarEnable;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Set<Company> getCompanies() {
        return companies;
    }

    public void setCompanies(Set<Company> companies) {
        this.companies = companies;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    public Set<Sanction> getSanctions() {
        return sanctions;
    }

    public void setSanctions(Set<Sanction> sanctions) {
        this.sanctions = sanctions;
    }
}
