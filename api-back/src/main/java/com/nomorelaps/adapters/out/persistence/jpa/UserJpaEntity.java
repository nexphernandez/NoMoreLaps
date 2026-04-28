package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Set;

/**
 * JPA Entity representing a User in the database.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "app_user")
public class UserJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "enabled")
    private boolean calendarEnable = false;

    @Column(name = "created_at")
    private LocalDateTime createAt;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private RoleJpaEntity role;

    @OneToMany(mappedBy = "user")
    private Set<CompanyJpaEntity> companies;

    @OneToMany(mappedBy = "user")
    private Set<ReservationJpaEntity> reservations;

    @OneToMany(mappedBy = "user")
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
     * @param calendarEnable user calendar enable
     * @param createAt user creation date
     * @param role user role
     * @param companies companies list
     * @param reservations reservations list
     * @param sanctions sanctions list
     */
    public UserJpaEntity(Long id, String name, String email, String password, boolean calendarEnable, 
        LocalDateTime createAt, RoleJpaEntity role, Set<CompanyJpaEntity> companies, 
        Set<ReservationJpaEntity> reservations, Set<SanctionJpaEntity> sanctions) {
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

    public boolean getCalendarEnable() {
        return this.calendarEnable;
    }

    public void setCalendarEnable(boolean calendarEnable) {
        this.calendarEnable = calendarEnable;
    }

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
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

    /**
     * Life-cycle callback method called before the entity is persisted.
     * Automatically sets the creation timestamp if it hasn't been set yet.
     */
    @jakarta.persistence.PrePersist
    protected void onCreate() {
        if (this.createAt == null) {
            this.createAt = java.time.LocalDateTime.now();
        }
    }
}
