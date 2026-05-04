package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.domain.models.DynamicPrice;

class DynamicPriceMapperTest {

    private final DynamicPriceMapper mapper = new DynamicPriceMapperImpl();

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        DynamicPriceRequest request = new DynamicPriceRequest();
        request.setDayOfWeek(1);
        request.setStartHour("08:00");
        request.setEndHour("20:00");
        request.setMinPrice(1.0);
        request.setMaxPrice(5.0);

        DynamicPrice domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals(1, domain.getDayOfWeek());
        assertEquals("08:00", domain.getStartHour());
        assertEquals(1.0, domain.getMinPrice());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        DynamicPrice domain = new DynamicPrice(1L);
        domain.setDayOfWeek(2);
        domain.setMinPrice(2.0);
        domain.setCreateAt(LocalDateTime.now());

        DynamicPriceResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(2, response.getDayOfWeek());
        assertEquals(2.0, response.getMinPrice());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        DynamicPrice domain = new DynamicPrice(1L);
        domain.setMaxPrice(10.0);

        DynamicPriceJpaEntity entity = mapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(10.0, entity.getMaxPrice());
    }

    @Test
    @DisplayName("toDomain - Should map jpa to domain")
    void shouldMapJpaToDomain() {
        DynamicPriceJpaEntity entity = new DynamicPriceJpaEntity();
        entity.setId(1L);
        entity.setDayOfWeek(3);

        DynamicPrice domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(3, domain.getDayOfWeek());
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
