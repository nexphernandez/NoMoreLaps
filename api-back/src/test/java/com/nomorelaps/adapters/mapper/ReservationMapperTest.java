package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;

class ReservationMapperTest {

    private final ReservationMapper reservationMapper = org.mapstruct.factory.Mappers.getMapper(ReservationMapper.class);


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain with nested IDs")
    void shouldMapRequestToDomain() {
        ReservationRequest request = new ReservationRequest();
        request.setUserId(10L);
        request.setParkingSpotId(20L);
        request.setPrice(15.0);
        request.setState("ACTIVE");

        Reservation domain = reservationMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals(15.0, domain.getPrice());
        assertEquals("ACTIVE", domain.getState());
        assertNotNull(domain.getUser());
        assertEquals(10L, domain.getUser().getId());
        assertNotNull(domain.getParkingSpot());
        assertEquals(20L, domain.getParkingSpot().getId());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response with nested properties")
    void shouldMapDomainToResponse() {
        User user = new User(10L);
        Parking parking = new Parking(5L);
        parking.setName("Main Parking");
        ParkingSpot spot = new ParkingSpot(20L);
        spot.setParking(parking);
        
        Reservation domain = new Reservation(1L);
        domain.setUser(user);
        domain.setParkingSpot(spot);
        domain.setPrice(15.0);
        domain.setState("ACTIVE");

        ReservationResponse response = reservationMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getUserId());
        assertEquals(20L, response.getParkingSpotId());
        assertEquals("Main Parking", response.getParkingName());
        assertEquals(15.0, response.getPrice());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        Reservation domain = new Reservation(1L);
        domain.setPrice(15.0);

        ReservationJpaEntity entity = reservationMapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals(15.0, entity.getPrice());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        ReservationJpaEntity entity = new ReservationJpaEntity();
        entity.setId(1L);
        entity.setPrice(15.0);

        Reservation domain = reservationMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(15.0, domain.getPrice());
    }

    @Test
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() {
        assertNull(reservationMapper.toDomainFromRequest(null));
        assertNull(reservationMapper.toResponse(null));
        assertNull(reservationMapper.toJpaEntity(null));
        assertNull(reservationMapper.toDomain(null));
    }
}

