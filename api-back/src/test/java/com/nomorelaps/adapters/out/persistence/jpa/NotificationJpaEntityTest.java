package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class NotificationJpaEntityTest {

    @Test
    @DisplayName("Should test all methods of NotificationJpaEntity including PrePersist")
    void testNotificationJpaEntity() {
        LocalDateTime now = LocalDateTime.now();
        NotificationJpaEntity n1 = new NotificationJpaEntity(1L, "msg", "type", true, 10L, now);
        
        assertEquals(1L, n1.getId());
        assertEquals("msg", n1.getMessage());
        assertEquals("type", n1.getType());
        assertTrue(n1.getIsRead());
        assertEquals(10L, n1.getCompanyId());
        assertEquals(now, n1.getCreatedAt());

        NotificationJpaEntity n2 = new NotificationJpaEntity(1L);
        
        assertEquals(n1, n1); 
        assertEquals(n1, n2);
        assertNotEquals(n1, new NotificationJpaEntity(2L));
        assertNotEquals(n1, null);
        assertNotEquals(n1, "other");
        assertEquals(n1.hashCode(), n2.hashCode());

        NotificationJpaEntity n3 = new NotificationJpaEntity(3L);
        assertNull(n3.getCreatedAt());
        n3.onCreate();
        assertNotNull(n3.getCreatedAt());
        
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        n3.setCreatedAt(past);
        n3.onCreate();
        assertEquals(past, n3.getCreatedAt());
    }
}
