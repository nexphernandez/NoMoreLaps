package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for UserRequest DTO.
 * Verifies property mapping and constructor initialization.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class UserRequestTest {

    private UserRequest userRequest;

    @BeforeEach
    void setUp() {
        userRequest = new UserRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithFullConstructor() {
        UserRequest fullRequest = new UserRequest("Alice", "alice@test.com", "secret123", true, "avatar.png", "555-1234");

        assertEquals("Alice", fullRequest.getName());
        assertEquals("alice@test.com", fullRequest.getEmail());
        assertEquals("secret123", fullRequest.getPassword());
        assertTrue(fullRequest.isCalendarEnable());
        assertEquals("avatar.png", fullRequest.getAvatar());
        assertEquals("555-1234", fullRequest.getPhone());
    }

    @Test
    @DisplayName("name - Should set and get user name")
    void shouldSetAndGetName() {
        userRequest.setName("Bob");
        assertEquals("Bob", userRequest.getName());
    }

    @Test
    @DisplayName("email - Should set and get email")
    void shouldSetAndGetEmail() {
        userRequest.setEmail("bob@test.com");
        assertEquals("bob@test.com", userRequest.getEmail());
    }

    @Test
    @DisplayName("password - Should set and get password")
    void shouldSetAndGetPassword() {
        userRequest.setPassword("newPass789");
        assertEquals("newPass789", userRequest.getPassword());
    }

    @Test
    @DisplayName("isCalendarEnable - Should set and get calendar status")
    void shouldSetAndGetCalendarEnable() {
        userRequest.setCalendarEnable(true);
        assertTrue(userRequest.isCalendarEnable());
        assertTrue(userRequest.getEnable(), "getEnable should return the same as isCalendarEnable");
    }

    @Test
    @DisplayName("avatar - Should set and get avatar path")
    void shouldSetAndGetAvatar() {
        userRequest.setAvatar("path/to/img.jpg");
        assertEquals("path/to/img.jpg", userRequest.getAvatar());
    }

    @Test
    @DisplayName("phone - Should set and get phone number")
    void shouldSetAndGetPhone() {
        userRequest.setPhone("987654321");
        assertEquals("987654321", userRequest.getPhone());
    }
}
