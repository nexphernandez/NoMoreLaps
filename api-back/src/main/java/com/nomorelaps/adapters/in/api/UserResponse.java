package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserResponse {
 
    private Long id;
    private String name;
    private String email;
    private String password;
    private boolean calendarEnable;
    private LocalDateTime createAt;


    /**
     * Empty constructor
     */
    public UserResponse() {
    }

    /**
     * Constructor with the parking primary key
     * @param id reservation id
     */
    public UserResponse(Long id){
        this.id=id;
    }
    /**
     * Constructor with all the attributes
     * @param id of the User
     * @param name of the User
     * @param email of the User
     * @param password of the User
     * @param enable of the User
     * @param createAt of the User
     */
    public UserResponse(Long id, String name, String email, String password, boolean enable, LocalDateTime createAt) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.calendarEnable = enable;
        this.createAt = createAt;
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

    public LocalDateTime getCreateAt() {
        return this.createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }

    @Override
    public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof UserResponse)) {
            return false;
        }
        UserResponse userResponse = (UserResponse) o;
        return Objects.equals(id, userResponse.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
