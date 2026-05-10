package com.nomorelaps.adapters.in.api;

/**
 * Data Transfer Object (DTO) representing an Authentication Response.
 * Encapsulates the authorization token sent back to the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class AuthResponse {
    
    private String token;
    private String message;
    private Long companyId;
    private String email;
    private String name;

    private Long userId;

    /**
     * Empty constructor
     */
    public AuthResponse() {
    }

    /**
     * Constructor with attributes.
     * 
     * @param token     The authorization token.
     * @param message   Response message.
     * @param companyId The ID of the company if applicable.
     * @param email     The user email.
     * @param name      The user name.
     * @param userId    The user ID.
     */
    public AuthResponse(String token, String message, Long companyId, String email, String name, Long userId) {
        this.token = token;
        this.message = message;
        this.companyId = companyId;
        this.email = email;
        this.name = name;
        this.userId = userId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
