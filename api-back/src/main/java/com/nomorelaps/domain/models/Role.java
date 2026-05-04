package com.nomorelaps.domain.models;

/**
 * Domain model representing a Role.
 * Contains the pure business logic and attributes, independent of databases or
 * APIs.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class Role {
    private Long id;
    private String name;
    private String description;

    /**
     * Empty constructor
     */
    public Role() {
    }

    /**
     * Constructor with the role primary key
     * @param id role id
     */
    public Role(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all the attributes
     * 
     * @param id          of the role
     * @param name        of the role
     * @param description of the role
     */
    public Role(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
