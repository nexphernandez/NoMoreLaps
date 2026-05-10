package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Unit tests for DynamicPriceMapper.
 * Verifies mapping between DynamicPrice domain models, API requests/responses, and JPA entities.
 */
class DynamicPriceMapperTest {

    private final DynamicPriceMapper mapper = Mappers.getMapper(DynamicPriceMapper.class);
    private DynamicPrice testPrice;

    @BeforeEach
    void setUp() {
        testPrice = new DynamicPrice(1L);
        testPrice.setDayOfWeek(1);
        testPrice.setStartHour("08:00");
        testPrice.setEndHour("20:00");
        testPrice.setMinPrice(1.0);
        testPrice.setMaxPrice(5.0);
        testPrice.setCreateAt(LocalDateTime.now());
    }

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
        DynamicPriceResponse response = mapper.toResponse(testPrice);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(1, response.getDayOfWeek());
        assertEquals(1.0, response.getMinPrice());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        DynamicPriceJpaEntity entity = mapper.toJpaEntity(testPrice);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(5.0, entity.getMaxPrice());
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
