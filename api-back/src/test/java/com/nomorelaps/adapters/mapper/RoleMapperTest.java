package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.domain.models.Role;

/**
 * Unit tests for RoleMapper.
 * Verifies mapping between Role domain models, API requests/responses, and JPA entities.
 */
class RoleMapperTest {

    private final RoleMapper mapper = Mappers.getMapper(RoleMapper.class);
    private Role testRole;

    @BeforeEach
    void setUp() {
        testRole = new Role(1L);
        testRole.setName("USER");
        testRole.setDescription("Standard user access");
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        RoleRequest request = new RoleRequest();
        request.setName("ADMIN");
        request.setDescription("All access");

        Role domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("ADMIN", domain.getName());
        assertEquals("All access", domain.getDescription());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        RoleResponse response = mapper.toResponse(testRole);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("USER", response.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        RoleJpaEntity entity = mapper.toJpaEntity(testRole);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("USER", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map jpa to domain")
    void shouldMapJpaToDomain() {
        RoleJpaEntity entity = new RoleJpaEntity();
        entity.setId(1L);
        entity.setName("MODERATOR");

        Role domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("MODERATOR", domain.getName());
    }

    @Test
    @DisplayName("toDomainFromRequest - Should return null when input is null")
    void shouldReturnNullWhenRequestIsNull() {
        assertNull(mapper.toDomainFromRequest(null));
    }

    @Test
    @DisplayName("toResponse - Should return null when input is null")
    void shouldReturnNullWhenDomainIsNullForResponse() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    @DisplayName("toJpaEntity - Should return null when input is null")
    void shouldReturnNullWhenDomainIsNullForJpa() {
        assertNull(mapper.toJpaEntity(null));
    }

    @Test
    @DisplayName("toDomain - Should return null when input is null")
    void shouldReturnNullWhenJpaIsNull() {
        assertNull(mapper.toDomain(null));
    }
}
