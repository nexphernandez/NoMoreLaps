package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

/**
 * Unit tests for NotificationJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class NotificationJpaEntityTest {

    private NotificationJpaEntity testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new NotificationJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new NotificationJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        NotificationJpaEntity entity = new NotificationJpaEntity(10L);
        assertEquals(10L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        NotificationJpaEntity entity = new NotificationJpaEntity(1L, "Alert", "INFO", true, 5L, now);

        assertEquals(1L, entity.getId());
        assertEquals("Alert", entity.getMessage());
        assertEquals("INFO", entity.getType());
        assertTrue(entity.getIsRead());
        assertEquals(5L, entity.getCompanyId());
        assertEquals(now, entity.getCreatedAt());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testNotification.setId(1L);
        assertEquals(1L, testNotification.getId());
        
        testNotification.setMessage("System update");
        assertEquals("System update", testNotification.getMessage());
        
        testNotification.setType("WARNING");
        assertEquals("WARNING", testNotification.getType());
        
        testNotification.setIsRead(true);
        assertTrue(testNotification.getIsRead());
        
        testNotification.setCompanyId(55L);
        assertEquals(55L, testNotification.getCompanyId());
        
        LocalDateTime now = LocalDateTime.now();
        testNotification.setCreatedAt(now);
        assertEquals(now, testNotification.getCreatedAt());
    }

    @Test
    @DisplayName("onCreate - Should set creation date if null")
    void shouldSetTimestampOnCreate() {
        assertNull(testNotification.getCreatedAt());
        testNotification.onCreate();
        assertNotNull(testNotification.getCreatedAt());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        NotificationJpaEntity n1 = new NotificationJpaEntity(1L);
        NotificationJpaEntity n2 = new NotificationJpaEntity(1L);
        NotificationJpaEntity n3 = new NotificationJpaEntity(2L);

        assertEquals(n1, n1);
        assertEquals(n1, n2);
        assertEquals(n1.hashCode(), n2.hashCode());
        assertNotEquals(n1, n3);
        assertNotEquals(n1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        NotificationJpaEntity n1 = new NotificationJpaEntity(null);
        NotificationJpaEntity n2 = new NotificationJpaEntity(null);
        assertEquals(n1, n2);
    }
}
