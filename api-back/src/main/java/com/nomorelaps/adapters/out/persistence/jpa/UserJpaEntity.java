package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.Objects;
import java.util.Set;

/**
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity(name = "user")
public class UserJpaEntity {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean enable;

    @Column(name = "created_at")
    private LocalDate createAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @OneToMany(mappedBy = "company")
    private Set<CompanyJpaEntity> companies;

    @OneToMany(mappedBy = "reservarion")
    private Set<ReservationJpaEntity> reservations;

    @OneToMany(mappedBy = "sanction")
    private Set<SanctionJpaEntity>sanctions;

/**
     * Empty constructor 
     */
    public UserJpaEntity() {
    }

    /**
     * Constructor with the user primary key
     * @param id user id
     */
    public UserJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with the user atributes
     * @param id user id
     * @param name user name
     * @param email user email
     * @param password user password
     * @param enable user enable
     * @param createAt user creation date
     * @param roles user roles
     * @param companies companies list
     * @param reservations reservations list
     * @param sanctions sanctions list
     */
    public UserJpaEntity(Long id, String name, String email, String password, boolean enable, 
        LocalDate createAt, RoleJpaEntity role, Set<CompanyJpaEntity> companies, 
        Set<ReservationJpaEntity> reservations, Set<SanctionJpaEntity> sanctions) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.enable = enable;
        this.createAt = createAt;
        this.role = role;
        this.companies = companies;
        this.reservations = reservations;
        this.sanctions = sanctions;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnable() {
        return this.enable;
    }

    public boolean getEnable() {
        return this.enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public LocalDate getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDate createAt) {
        this.createAt = createAt;
    }

    public RoleJpaEntity getRole() {
        return this.role;
    }

    public void setRole(RoleJpaEntity role) {
        this.role = role;
    }

    public Set<CompanyJpaEntity> getCompanies() {
        return this.companies;
    }

    public void setCompanies(Set<CompanyJpaEntity> companies) {
        this.companies = companies;
    }

    public Set<ReservationJpaEntity> getReservations() {
        return this.reservations;
    }

    public void setReservations(Set<ReservationJpaEntity> reservations) {
        this.reservations = reservations;
    }

    public Set<SanctionJpaEntity> getSanctions() {
        return this.sanctions;
    }

    public void setSanctions(Set<SanctionJpaEntity> sanctions) {
        this.sanctions = sanctions;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserJpaEntity)) {
            return false;
        }
        UserJpaEntity userJpaEntity = (UserJpaEntity) o;
        return Objects.equals(id, userJpaEntity.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
