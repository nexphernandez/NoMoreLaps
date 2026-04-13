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

    public AuthResponse() {
    }

    public AuthResponse(String token, String message) {
        this.token = token;
        this.message = message;
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
}
