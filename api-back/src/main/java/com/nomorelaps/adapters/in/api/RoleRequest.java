package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating a Role.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class RoleRequest {

    @NotBlank(message = "El nombre del rol es obligatorio")
    private String name;

    @NotBlank(message = "La descripción del rol es obligatoria")
    private String description;

    /**
     * Empty constructor
     */
    public RoleRequest() {
    }

    /**
     * Constructor with all the attributes
     * @param name of the role
     * @param description of the role
     */
    public RoleRequest(String name, String description) {
        this.name = name;
        this.description = description;
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
