package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ChangePasswordRequest DTO.
 * Tests are granular to verify each property and constructor independently.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ChangePasswordRequestTest {

    private ChangePasswordRequest request;

    @BeforeEach
    void setUp() {
        request = new ChangePasswordRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize with all arguments")
    void shouldInitializeWithConstructor() {
        String currentPass = "oldPassword123";
        String newPass = "newPassword456";

        ChangePasswordRequest constructorRequest = new ChangePasswordRequest(currentPass, newPass);

        assertEquals(currentPass, constructorRequest.getCurrentPassword(), "Current password should match constructor argument");
        assertEquals(newPass, constructorRequest.getNewPassword(), "New password should match constructor argument");
    }

    @Test
    @DisplayName("currentPassword - Should correctly set and get the current password")
    void shouldSetAndGetCurrentPassword() {
        String currentPass = "myCurrentSecret";

        request.setCurrentPassword(currentPass);

        assertEquals(currentPass, request.getCurrentPassword(), "Getter should return the value set by the setter");
    }

    @Test
    @DisplayName("newPassword - Should correctly set and get the new password")
    void shouldSetAndGetNewPassword() {
        String newPass = "myNewSecret123";

        request.setNewPassword(newPass);

        assertEquals(newPass, request.getNewPassword(), "Getter should return the value set by the setter");
    }
}
