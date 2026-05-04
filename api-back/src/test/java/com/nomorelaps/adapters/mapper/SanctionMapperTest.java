package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.SanctionRequest;
import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.domain.models.Sanction;

class SanctionMapperTest {

    private final SanctionMapper mapper = new SanctionMapperImpl();


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        SanctionRequest request = new SanctionRequest();
        request.setAmount(50.0);
        request.setReason("Late");

        Sanction domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals(50.0, domain.getAmount());
        assertEquals("Late", domain.getReason());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        Sanction domain = new Sanction(1L);
        domain.setAmount(25.0);

        SanctionResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(25.0, response.getAmount());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        Sanction domain = new Sanction(1L);
        domain.setReason("Damage");

        SanctionJpaEntity entity = mapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Damage", entity.getReason());
    }

    @Test
    @DisplayName("toDomain - Should map jpa to domain")
    void shouldMapJpaToDomain() {
        SanctionJpaEntity entity = new SanctionJpaEntity();
        entity.setId(1L);
        entity.setPaid(true);

        Sanction domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertTrue(domain.isPaid());
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
