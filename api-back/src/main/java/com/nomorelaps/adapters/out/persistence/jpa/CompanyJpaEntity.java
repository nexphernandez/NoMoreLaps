package com.nomorelaps.adapters.out.persistence.jpa;


import java.time.LocalDate;
import java.util.Objects;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
/**
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity(name = "company")
public class CompanyJpaEntity {
    @Id
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "api_key")
    private String password;

    @Column(name = "phone")
    private String phone;

    @Column(name = "corporate_email")
    private String email;

    @Column(name = "cif")
    private String cif;

    @Column(name = "created_at")
    private LocalDate registerDay;

    @ManyToOne
    @JoinColumn(name="user_id")
    private UserJpaEntity user;
    
    @OneToMany(mappedBy = "parking")
    private Set<ParkingJpaEntity>parkins;


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
     * Constructor with the atributes of the company
     * @param id company id
     * @param user ompany user
     * @param name company name
     * @param password company password
     * @param phone company phone
     * @param email company email
     * @param cif company cif
     * @param registerDay company register day
     * @param user company user
     * @param parkins company parkins
     */
    public CompanyJpaEntity(Long id, String name, String password, String phone, String email, String cif, LocalDate registerDay, 
        UserJpaEntity user, Set<ParkingJpaEntity> parkins) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.cif = cif;
        this.registerDay = registerDay;
        this.user = user;
        this.parkins = parkins;
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

    public LocalDate getRegisterDay() {
        return this.registerDay;
    }

    public void setRegisterDay(LocalDate registerDay) {
        this.registerDay = registerDay;
    }

    public UserJpaEntity getUser() {
        return this.user;
    }

    public void setUser(UserJpaEntity user) {
        this.user = user;
    }

    public Set<ParkingJpaEntity> getParkins() {
        return this.parkins;
    }

    public void setParkins(Set<ParkingJpaEntity> parkins) {
        this.parkins = parkins;
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

}
