package com.nomorelaps.adapters.out.persistence.jpa;


import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
/**
 * JPA Entity representing a Company in the database.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "company")
public class CompanyJpaEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "password")
    private String password;

    @Column(name = "api_key", unique = true)
    private String apiKey;

    @Column(name = "phone")
    private String phone;

    @Column(name = "corporate_email")
    private String email;

    @Column(name = "cif")
    private String cif;

    @Column(name = "created_at")
    private LocalDateTime registerDay;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserJpaEntity user;
    
    @OneToMany(mappedBy = "company")
    private Set<ParkingJpaEntity>parkings;


    /**
     * Empty constructor
     */
    public CompanyJpaEntity() {
    }

    /**
     * Constructor with the primary key of the Company
     * @param id identifier of the company
     */
    public CompanyJpaEntity(Long id){
        this.id = id;
    }

    /**
     * Constructor with the attributes of the company.
     * 
     * @param id company id
     * @param name company name
     * @param password company password
     * @param apiKey company apiKey
     * @param phone company phone
     * @param email company email
     * @param cif company cif
     * @param registerDay company register day
     * @param user company user
     * @param parkings company parkings
     */
    public CompanyJpaEntity(Long id, String name, String password,String apiKey, String phone, 
            String email, String cif, LocalDateTime registerDay, 
            UserJpaEntity user, Set<ParkingJpaEntity> parkings) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.apiKey = apiKey;
        this.phone = phone;
        this.email = email;
        this.cif = cif;
        this.registerDay = registerDay;
        this.user = user;
        this.parkings = parkings;
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

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

        public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCif() {
        return this.cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

    public LocalDateTime getRegisterDay() {
        return this.registerDay;
    }

    public void setRegisterDay(LocalDateTime registerDay) {
        this.registerDay = registerDay;
    }

    public UserJpaEntity getUser() {
        return this.user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public Set<ParkingJpaEntity> getParkings() {
        return this.parkings;
    }

    public void setParkings(Set<ParkingJpaEntity> parkings) {
        this.parkings = parkings;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof CompanyJpaEntity)) {
            return false;
        }
        CompanyJpaEntity companyJpaEntity = (CompanyJpaEntity) o;
        return Objects.equals(id, companyJpaEntity.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Life-cycle callback method called before the entity is persisted.
     * Automatically sets the registration date if it hasn't been set yet.
     */
    @PrePersist
    protected void onCreate() {
        if (this.registerDay == null) {
            this.registerDay = LocalDateTime.now();
        }
    }
}


