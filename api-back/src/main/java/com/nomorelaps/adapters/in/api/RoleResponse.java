package com.nomorelaps.adapters.in.api;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a Role in API responses.
 * Encapsulates the structured data sent back to the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class RoleResponse {

    private Long id;
    private String name;
    private String description;

    /**
     * Empty constructor
     */
    public RoleResponse() {
    }

    /**
     * Constructor with the role primary key
     * @param id role id
     */
    public RoleResponse(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all the attributes
     * @param id of the role
     * @param name of the role
     * @param description of the role
     */
    public RoleResponse(Long id, String name, String description) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RoleResponse)) return false;
        RoleResponse that = (RoleResponse) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
