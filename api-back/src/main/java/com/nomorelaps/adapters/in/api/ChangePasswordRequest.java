package com.nomorelaps.adapters.in.api;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for password change request.
 * Encapsulates the current and new password data.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
public class ChangePasswordRequest {

    @NotBlank(message = "Current password is required")
    private String currentPassword;

    @NotBlank(message = "New password is required")
    private String newPassword;

    /**
     * Empty constructor
     */
    public ChangePasswordRequest() {
    }

    /**
     * Constructor with attributes.
     * 
     * @param currentPassword The current password of the user.
     * @param newPassword     The new password to set.
     */
    public ChangePasswordRequest(String currentPassword, String newPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
