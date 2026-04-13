package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating a Company.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class CompanyRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;

    private String apiKey;

    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;

    private String phone;

    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Formato de email incorrecto")
    private String email;

    @NotBlank(message = "El CIF no puede estar vacío")
    private String cif;

    /**
     * Empty constructor
     */
    public CompanyRequest() {
    }
    /**
     * Constructor with all the attributes of the Company
     * @param name name of the company
     * @param apiKey api key of the company
     * @param password of the company
     * @param phone phone number of the company
     * @param email corporate email of the company
     * @param cif cif of the company
     */
    public CompanyRequest( String name,String apiKey, String password, String phone,
                          String email, String cif) {
        this.name = name;
        this.apiKey = apiKey;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.cif = cif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApiKey(){
        return apiKey;
    }

    public void setApiKey(String apiKey){
        this.apiKey=apiKey;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCif() {
        return cif;
    }

    public void setCif(String cif) {
        this.cif = cif;
    }

}
