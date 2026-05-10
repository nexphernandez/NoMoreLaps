package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for UserResponse DTO.
 * Separates field mapping, constructors, and equality logic into granular tests.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class UserResponseTest {

    private UserResponse userResponse;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        userResponse = new UserResponse();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        UserResponse idResponse = new UserResponse(100L);
        assertEquals(100L, idResponse.getId());
    }

    @Test
    @DisplayName("Full Constructor - Should map all fields correctly")
    void shouldInitializeWithFullConstructor() {
        UserResponse fullResponse = new UserResponse(1L, "Alice", "alice@test.com", "secret", true, "avatar.png", "555-1234", now);

        assertEquals(1L, fullResponse.getId());
        assertEquals("Alice", fullResponse.getName());
        assertEquals("alice@test.com", fullResponse.getEmail());
        assertEquals("secret", fullResponse.getPassword());
        assertTrue(fullResponse.isCalendarEnable());
        assertEquals("avatar.png", fullResponse.getAvatar());
        assertEquals("555-1234", fullResponse.getPhone());
        assertEquals(now, fullResponse.getCreateAt());
    }

    @Test
    @DisplayName("id - Should set and get user ID")
    void shouldSetAndGetId() {
        userResponse.setId(25L);
        assertEquals(25L, userResponse.getId());
    }

    @Test
    @DisplayName("name - Should set and get name")
    void shouldSetAndGetName() {
        userResponse.setName("Bob");
        assertEquals("Bob", userResponse.getName());
    }

    @Test
    @DisplayName("email - Should set and get email")
    void shouldSetAndGetEmail() {
        userResponse.setEmail("bob@test.com");
        assertEquals("bob@test.com", userResponse.getEmail());
    }

    @Test
    @DisplayName("password - Should set and get password")
    void shouldSetAndGetPassword() {
        userResponse.setPassword("pass123");
        assertEquals("pass123", userResponse.getPassword());
    }

    @Test
    @DisplayName("calendarEnable - Should set and get calendar status")
    void shouldSetAndGetCalendarEnable() {
        userResponse.setCalendarEnable(true);
        assertTrue(userResponse.isCalendarEnable());
        assertTrue(userResponse.getEnable());
        
        userResponse.setCalendarEnable(false);
        assertFalse(userResponse.isCalendarEnable());
        assertFalse(userResponse.getEnable());
    }

    @Test
    @DisplayName("avatar - Should set and get avatar path")
    void shouldSetAndGetAvatar() {
        userResponse.setAvatar("img.png");
        assertEquals("img.png", userResponse.getAvatar());
    }

    @Test
    @DisplayName("phone - Should set and get phone")
    void shouldSetAndGetPhone() {
        userResponse.setPhone("123456789");
        assertEquals("123456789", userResponse.getPhone());
    }

    @Test
    @DisplayName("createAt - Should set and get creation timestamp")
    void shouldSetAndGetCreateAt() {
        userResponse.setCreateAt(now);
        assertEquals(now, userResponse.getCreateAt());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        UserResponse firstUser = new UserResponse(100L);
        UserResponse secondUser = new UserResponse(100L);
        UserResponse thirdUser = new UserResponse(200L);

        assertEquals(firstUser, firstUser, "Should be equal to itself");
        assertEquals(firstUser, secondUser, "Should be equal if IDs match");
        assertNotEquals(firstUser, thirdUser, "Should not be equal if IDs differ");
        assertNotEquals(firstUser, null, "Should not be equal to null");
        assertNotEquals(firstUser, new Object(), "Should not be equal to other types");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same ID")
    void shouldHaveConsistentHashCode() {
        UserResponse firstUser = new UserResponse(100L);
        UserResponse secondUser = new UserResponse(100L);

        assertEquals(firstUser.hashCode(), secondUser.hashCode(), "Same ID should produce same hash code");
    }
}
