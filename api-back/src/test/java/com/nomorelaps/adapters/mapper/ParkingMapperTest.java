package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.domain.models.Parking;

class ParkingMapperTest {

    private final ParkingMapper parkingMapper = new ParkingMapperImpl();


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        ParkingRequest request = new ParkingRequest();
        request.setName("Central Parking");
        request.setAddress("Street 1");
        request.setLatitude(40.0);
        request.setLongitude(-3.0);

        Parking domain = parkingMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Central Parking", domain.getName());
        assertEquals("Street 1", domain.getAddress());
        assertEquals(40.0, domain.getLatitude());
        assertEquals(-3.0, domain.getLongitude());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        Parking domain = new Parking(1L);
        domain.setName("Central Parking");

        ParkingResponse response = parkingMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Central Parking", response.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        Parking domain = new Parking(1L);
        domain.setName("Central Parking");

        ParkingJpaEntity entity = parkingMapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Central Parking", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        ParkingJpaEntity entity = new ParkingJpaEntity();
        entity.setId(1L);
        entity.setName("Central Parking");

        Parking domain = parkingMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Central Parking", domain.getName());
    }

    @Test
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() {
        assertNull(parkingMapper.toDomainFromRequest(null));
        assertNull(parkingMapper.toResponse(null));
        assertNull(parkingMapper.toJpaEntity(null));
        assertNull(parkingMapper.toDomain(null));
    }
}

