package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for creating or updating a User.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class UserRequest {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email correcto")
    private String email;
    private String password;
    private boolean calendarEnable;
    private String avatar;
    private String phone;


    /**
     * Empty constructor
     */
    public UserRequest() {
    }

    /**
     * Constructor with all the attributes
     * @param name of the User
     * @param email of the User
     * @param password of the User
     * @param enable status of the User calendar
     * @param avatar profile image of the User
     * @param phone phone number of the User
     */
    public UserRequest(String name, String email, String password, boolean enable, String avatar, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.calendarEnable = enable;
        this.avatar = avatar;
        this.phone = phone;
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

    public boolean isCalendarEnable() {
        return this.calendarEnable;
    }

    public boolean getEnable() {
        return this.calendarEnable;
    }

    public void setCalendarEnable(boolean enable) {
        this.calendarEnable = enable;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
