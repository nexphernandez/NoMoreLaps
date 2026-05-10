package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.NotificationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.NotificationJpaEntity;
import com.nomorelaps.domain.models.Notification;

/**
 * Unit tests for NotificationMapper.
 * Verifies mapping between Notification domain models, API responses, and JPA entities.
 */
class NotificationMapperTest {

    private final NotificationMapper mapper = Mappers.getMapper(NotificationMapper.class);
    private Notification testNotification;

    @BeforeEach
    void setUp() {
        testNotification = new Notification(1L);
        testNotification.setMessage("System Alert");
        testNotification.setIsRead(false);
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToJpa() {
        NotificationJpaEntity entity = mapper.toJpaEntity(testNotification);
        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("System Alert", entity.getMessage());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        NotificationJpaEntity entity = new NotificationJpaEntity();
        entity.setId(1L);
        entity.setMessage("System Alert");

        Notification domain = mapper.toDomain(entity);
        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("System Alert", domain.getMessage());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        NotificationResponse response = mapper.toResponse(testNotification);
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("System Alert", response.getMessage());
    }

    @Test
    @DisplayName("toDomain - Should return null when input is null")
    void shouldReturnNullWhenJpaIsNull() {
        assertNull(mapper.toDomain(null));
    }

    @Test
    @DisplayName("toJpaEntity - Should return null when input is null")
    void shouldReturnNullWhenDomainIsNullForJpa() {
        assertNull(mapper.toJpaEntity(null));
    }

    @Test
    @DisplayName("toResponse - Should return null when input is null")
    void shouldReturnNullWhenDomainIsNullForResponse() {
        assertNull(mapper.toResponse(null));
    }
}
