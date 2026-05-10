package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Notification domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class NotificationTest {

    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new Notification(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        Notification notification = new Notification();
        assertNull(notification.getId());
        assertFalse(notification.getIsRead());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        Notification notification = new Notification(5L);
        assertEquals(5L, notification.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Notification notification = new Notification(1L, "Msg", "Type", true, 10L, now);
        
        assertEquals(1L, notification.getId());
        assertEquals("Msg", notification.getMessage());
        assertEquals("Type", notification.getType());
        assertTrue(notification.getIsRead());
        assertEquals(10L, notification.getCompanyId());
        assertEquals(now, notification.getCreatedAt());
    }


    @Test
    @DisplayName("Getter/Setter - Message: Should preserve string value")
    void shouldSetAndGetMessage() {
        testNotification.setMessage("Hello");
        assertEquals("Hello", testNotification.getMessage());
    }

    @Test
    @DisplayName("Getter/Setter - Type: Should preserve string value")
    void shouldSetAndGetType() {
        testNotification.setType("INFO");
        assertEquals("INFO", testNotification.getType());
    }

    @Test
    @DisplayName("Getter/Setter - IsRead: Should preserve boolean value")
    void shouldSetAndGetIsRead() {
        testNotification.setIsRead(true);
        assertTrue(testNotification.getIsRead());
    }

    @Test
    @DisplayName("Getter/Setter - CompanyId: Should preserve identifier")
    void shouldSetAndGetCompanyId() {
        testNotification.setCompanyId(100L);
        assertEquals(100L, testNotification.getCompanyId());
    }

    @Test
    @DisplayName("Getter/Setter - CreatedAt: Should preserve timestamp")
    void shouldSetAndGetCreatedAt() {
        LocalDateTime now = LocalDateTime.now();
        testNotification.setCreatedAt(now);
        assertEquals(now, testNotification.getCreatedAt());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testNotification, testNotification);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testNotification, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testNotification, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        Notification other = new Notification(1L);
        assertEquals(testNotification, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        Notification other = new Notification(2L);
        assertNotEquals(testNotification, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        Notification n1 = new Notification();
        Notification n2 = new Notification();
        assertEquals(n1, n2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        Notification n1 = new Notification(1L);
        Notification n2 = new Notification();
        assertNotEquals(n1, n2);
        assertNotEquals(n2, n1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        Notification other = new Notification(1L);
        assertEquals(testNotification.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        Notification other = new Notification(2L);
        assertNotEquals(testNotification.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        Notification n1 = new Notification();
        Notification n2 = new Notification();
        assertEquals(n1.hashCode(), n2.hashCode());
    }
}
