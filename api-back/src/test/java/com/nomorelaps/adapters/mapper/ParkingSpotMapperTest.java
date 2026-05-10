package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Unit tests for ParkingSpotMapper.
 * Verifies mapping between ParkingSpot domain models, API requests/responses, and JPA entities.
 */
class ParkingSpotMapperTest {

    private final ParkingSpotMapper mapper = Mappers.getMapper(ParkingSpotMapper.class);

    @Mock
    private ParkingMapper parkingMapper;

    private ParkingSpot testSpot;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(mapper, "parkingMapper", parkingMapper);
        
        testSpot = new ParkingSpot(1L);
        testSpot.setNumber(101);
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        ParkingSpotRequest request = new ParkingSpotRequest();
        request.setNumber(202);

        ParkingSpot domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals(202, domain.getNumber());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        ParkingSpotResponse response = mapper.toResponse(testSpot);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(101, response.getNumber());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        ParkingSpotJpaEntity entity = mapper.toJpaEntity(testSpot);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(101, entity.getNumber());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        ParkingSpotJpaEntity entity = new ParkingSpotJpaEntity();
        entity.setId(1L);
        entity.setNumber(303);

        ParkingSpot domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(303, domain.getNumber());
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
