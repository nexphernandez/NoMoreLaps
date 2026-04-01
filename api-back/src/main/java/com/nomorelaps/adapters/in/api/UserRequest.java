package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class UserRequest {
    
    @NotBlank(message = "El nombre no puede estar vacío")
    private String name;
    @NotBlank(message = "El email no puede estar vacío")
    @Email(message = "Debe ser un formato de email correcto")
    private String email;
    @NotBlank(message = "La contraseña no puede estar vacía")
    private String password;
    private boolean calendarEnable;


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
     */
    public UserRequest(String name, String email, String password, boolean enable) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.calendarEnable = enable;
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

}
