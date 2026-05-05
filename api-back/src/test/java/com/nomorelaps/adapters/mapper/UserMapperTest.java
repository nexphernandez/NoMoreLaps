package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.domain.models.User;

class UserMapperTest {

    private final UserMapper userMapper = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        UserRequest request = new UserRequest();
        request.setName("Alice");
        request.setEmail("alice@test.com");
        request.setCalendarEnable(true);

        User domain = userMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Alice", domain.getName());
        assertEquals("alice@test.com", domain.getEmail());
        assertTrue(domain.isCalendarEnable());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        User domain = new User(1L);
        domain.setName("Alice");
        domain.setEmail("alice@test.com");

        UserResponse response = userMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Alice", response.getName());
        assertEquals("alice@test.com", response.getEmail());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        User domain = new User(1L);
        domain.setName("Alice");

        UserJpaEntity entity = userMapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Alice", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("Alice");

        User domain = userMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Alice", domain.getName());
    }

    @Test
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() {
        assertNull(userMapper.toDomainFromRequest(null));
        assertNull(userMapper.toResponse(null));
        assertNull(userMapper.toJpaEntity(null));
        assertNull(userMapper.toDomain(null));
    }
}

