package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.domain.models.ParkingSpot;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

class ParkingSpotMapperTest {

    private final ParkingSpotMapper mapper = Mappers.getMapper(ParkingSpotMapper.class);

    @Mock
    private ParkingMapper parkingMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(mapper, "parkingMapper", parkingMapper);
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        ParkingSpotRequest request = new ParkingSpotRequest();
        request.setNumber(101);

        ParkingSpot domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals(101, domain.getNumber());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        ParkingSpot domain = new ParkingSpot(1L);
        domain.setNumber(102);

        ParkingSpotResponse response = mapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(102, response.getNumber());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        ParkingSpot domain = new ParkingSpot(1L);
        domain.setNumber(103);

        ParkingSpotJpaEntity entity = mapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(103, entity.getNumber());
    }

    @Test
    @DisplayName("toDomain - Should map jpa to domain")
    void shouldMapJpaToDomain() {
        ParkingSpotJpaEntity entity = new ParkingSpotJpaEntity();
        entity.setId(1L);
        entity.setNumber(104);

        ParkingSpot domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(104, domain.getNumber());
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
