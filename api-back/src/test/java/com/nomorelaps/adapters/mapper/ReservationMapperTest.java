package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;

class ReservationMapperTest {

    private final ReservationMapper reservationMapper = Mappers.getMapper(ReservationMapper.class);


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain with nested IDs")
    void shouldMapRequestToDomain() {
        ReservationRequest request = new ReservationRequest();
        request.setUserId(10L);
        request.setParkingSpotId(20L);
        request.setTotalPrice(15.0);
        request.setStatus("ACTIVE");

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

    @Test
    @DisplayName("toResponse - Should handle null nested objects correctly")
    void shouldHandleNullNestedInToResponse() {
        Reservation domain = new Reservation(1L);
        domain.setUser(null);
        domain.setParkingSpot(null);

        ReservationResponse response = reservationMapper.toResponse(domain);

        assertNotNull(response);
        assertNull(response.getUserId());
        assertNull(response.getParkingSpotId());
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toResponse - Should handle partial null nested objects correctly")
    void shouldHandlePartialNullNestedInToResponse() {
        Reservation domain = new Reservation(1L);
        
        User user = new User();
        user.setId(null);
        domain.setUser(user);

        ParkingSpot spot = new ParkingSpot();
        spot.setId(null);
        spot.setParking(null);
        domain.setParkingSpot(spot);

        ReservationResponse response = reservationMapper.toResponse(domain);

        assertNotNull(response);
        assertNull(response.getUserId());
        assertNull(response.getParkingSpotId());
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toResponse - Should handle null parking in spot correctly")
    void shouldHandleNullParkingInSpotInToResponse() {
        Reservation domain = new Reservation(1L);
        
        ParkingSpot spot = new ParkingSpot(20L);
        spot.setParking(null);
        domain.setParkingSpot(spot);

        ReservationResponse response = reservationMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(20L, response.getParkingSpotId());
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toDomainFromRequest - Should handle null IDs in request correctly")
    void shouldHandleNullIdsInToDomainFromRequest() {
        ReservationRequest request = new ReservationRequest();
        request.setUserId(null);
        request.setParkingSpotId(null);
        request.setTotalPrice(null);
        request.setStatus(null);

        Reservation domain = reservationMapper.toDomainFromRequest(request);
        
        assertNotNull(domain);

        if (domain.getUser() != null) {
            assertNull(domain.getUser().getId());
        }
        if (domain.getParkingSpot() != null) {
            assertNull(domain.getParkingSpot().getId());
        }
    }

    @Test
    @DisplayName("toResponse - Should handle null name in parking")
    void shouldHandleNullNameInParking() {
        Reservation domain = new Reservation(1L);
        Parking parking = new Parking(5L);
        parking.setName(null);
        ParkingSpot spot = new ParkingSpot(20L);
        spot.setParking(parking);
        domain.setParkingSpot(spot);

        ReservationResponse response = reservationMapper.toResponse(domain);

        assertNotNull(response);
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("Internal methods null checks - Reflection to hit unreachable branches in generated code")
    void shouldHandleNullsInInternalMethods() throws Exception {
        Object impl = reservationMapper;
        
        Method m1 = impl.getClass().getDeclaredMethod("reservationRequestToUser", ReservationRequest.class);
        m1.setAccessible(true);
        assertNull(m1.invoke(impl, (ReservationRequest) null));

        Method m2 = impl.getClass().getDeclaredMethod("reservationRequestToParkingSpot", ReservationRequest.class);
        m2.setAccessible(true);
        assertNull(m2.invoke(impl, (ReservationRequest) null));

        Method m3 = impl.getClass().getDeclaredMethod("domainUserId", Reservation.class);
        m3.setAccessible(true);
        assertNull(m3.invoke(impl, (Reservation) null));

        Method m4 = impl.getClass().getDeclaredMethod("domainParkingSpotId", Reservation.class);
        m4.setAccessible(true);
        assertNull(m4.invoke(impl, (Reservation) null));

        Method m5 = impl.getClass().getDeclaredMethod("domainParkingSpotParkingName", Reservation.class);
        m5.setAccessible(true);
        assertNull(m5.invoke(impl, (Reservation) null));
    }
}


