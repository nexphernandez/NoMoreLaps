package com.nomorelaps.adapters.out.persistence.jpa;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.Objects;
import java.util.Set;

/**
 * JPA Entity representing a Role in the database.
 * 
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "role")
public class RoleJpaEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name",nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "role")
    private Set<UserJpaEntity> users;

    /**
     * Empty constructor
     */
    public RoleJpaEntity() {
    }

    /**
     * Constructor with the Role primary key
     * @param id role id
     */
    public RoleJpaEntity(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all the Role parameters
     * @param id role id
     * @param name role name
     * @param users list of users with this role
     */
    public RoleJpaEntity(Long id, String name, Set<UserJpaEntity> users) {
        this.id = id;
        this.name = name;
        this.users = users;
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

    public Set<UserJpaEntity> getUsers() {
        return this.users;
    }

    public void setUsers(Set<UserJpaEntity> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof RoleJpaEntity)) {
            return false;
        }
        RoleJpaEntity roleJpaEntity = (RoleJpaEntity) o;
        return Objects.equals(id, roleJpaEntity.id) ;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
