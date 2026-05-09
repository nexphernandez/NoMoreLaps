package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.NotificationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.NotificationJpaEntity;
import com.nomorelaps.domain.models.Notification;

class NotificationMapperTest {

    private final NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);

    @Test
    @DisplayName("Should map between Notification entities and domain")
    void shouldMapNotification() {
        Notification domain = new Notification(1L);
        domain.setMessage("Test");
        
        NotificationJpaEntity entity = mapper.toJpaEntity(domain);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Test", entity.getMessage());

        Notification domain2 = mapper.toDomain(entity);
        assertNotNull(domain2);
        assertEquals(1L, domain2.getId());
        assertEquals("Test", domain2.getMessage());

        NotificationResponse response = mapper.toResponse(domain);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test", response.getMessage());
    }

    @Test
    @DisplayName("Should handle nulls in NotificationMapper")
    void shouldHandleNulls() {
        assertNull(mapper.toDomain(null));
        assertNull(mapper.toJpaEntity(null));
        assertNull(mapper.toResponse(null));
    }
}
