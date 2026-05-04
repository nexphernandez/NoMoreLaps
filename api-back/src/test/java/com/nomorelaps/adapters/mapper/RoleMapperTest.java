package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.domain.models.Role;

class RoleMapperTest {

    private final RoleMapper mapper = new RoleMapperImpl();


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
        Role domain = new Role(1L);
        domain.setName("USER");

        RoleResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("USER", response.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        Role domain = new Role(1L);
        domain.setName("GUEST");

        RoleJpaEntity entity = mapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("GUEST", entity.getName());
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
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() {
        assertNull(mapper.toDomainFromRequest(null));
        assertNull(mapper.toResponse(null));
        assertNull(mapper.toJpaEntity(null));
        assertNull(mapper.toDomain(null));
    }
}
