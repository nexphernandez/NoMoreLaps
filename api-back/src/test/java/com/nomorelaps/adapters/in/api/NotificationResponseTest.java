package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for NotificationResponse DTO.
 * Verifies constructors and individual property accessors.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class NotificationResponseTest {

    private NotificationResponse response;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        response = new NotificationResponse();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        NotificationResponse idResponse = new NotificationResponse(100L);

        assertEquals(100L, idResponse.getId(), "ID should match constructor argument");
    }

    @Test
    @DisplayName("Full Constructor - Should initialize all fields correctly")
    void shouldInitializeWithFullConstructor() {
        NotificationResponse fullResponse = new NotificationResponse(1L, "System Alert", "INFO", true, 50L, now);

        assertEquals(1L, fullResponse.getId());
        assertEquals("System Alert", fullResponse.getMessage());
        assertEquals("INFO", fullResponse.getType());
        assertTrue(fullResponse.getIsRead());
        assertEquals(50L, fullResponse.getCompanyId());
        assertEquals(now, fullResponse.getCreatedAt());
    }

    @Test
    @DisplayName("id - Should set and get ID")
    void shouldSetAndGetId() {
        response.setId(5L);
        assertEquals(5L, response.getId());
    }

    @Test
    @DisplayName("message - Should set and get message")
    void shouldSetAndGetMessage() {
        String msg = "New Notification";
        response.setMessage(msg);
        assertEquals(msg, response.getMessage());
    }

    @Test
    @DisplayName("type - Should set and get type")
    void shouldSetAndGetType() {
        response.setType("WARNING");
        assertEquals("WARNING", response.getType());
    }

    @Test
    @DisplayName("isRead - Should set and get read status")
    void shouldSetAndGetIsRead() {
        response.setIsRead(true);
        assertTrue(response.getIsRead());
        
        response.setIsRead(false);
        assertFalse(response.getIsRead());
    }

    @Test
    @DisplayName("companyId - Should set and get company ID")
    void shouldSetAndGetCompanyId() {
        response.setCompanyId(200L);
        assertEquals(200L, response.getCompanyId());
    }

    @Test
    @DisplayName("createdAt - Should set and get creation timestamp")
    void shouldSetAndGetCreatedAt() {
        response.setCreatedAt(now);
        assertEquals(now, response.getCreatedAt());
    }
}
