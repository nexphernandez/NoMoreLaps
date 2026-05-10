package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for ReservationMapper.
 * Verifies mapping between Reservation domain models, API requests/responses, and JPA entities.
 * Covers deep nested relationships (User, ParkingSpot, Parking) and internal MapStruct logic.
 */
class ReservationMapperTest {

    private final ReservationMapper mapper = Mappers.getMapper(ReservationMapper.class);
    
    private Reservation testReservation;
    private User testUser;
    private ParkingSpot testParkingSpot;
    private Parking testParking;

    @BeforeEach
    void setUp() {
        testUser = new User(10L);
        testUser.setName("Alice");

        testParking = new Parking(5L);
        testParking.setName("Grand Central");

        testParkingSpot = new ParkingSpot(20L);
        testParkingSpot.setParking(testParking);

        testReservation = new Reservation(1L);
        testReservation.setUser(testUser);
        testReservation.setParkingSpot(testParkingSpot);
        testReservation.setPrice(15.0);
        testReservation.setState("ACTIVE");
        testReservation.setBasePrice(10.0);
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain with nested IDs")
    void shouldMapRequestToDomain() {
        ReservationRequest request = new ReservationRequest();
        request.setUserId(99L);
        request.setParkingSpotId(88L);
        request.setPrice(50.0);
        request.setState("PENDING");

        Reservation domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals(50.0, domain.getPrice());
        assertEquals("PENDING", domain.getState());
        assertEquals(99L, domain.getUser().getId());
        assertEquals(88L, domain.getParkingSpot().getId());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response with nested properties")
    void shouldMapDomainToResponse() {
        ReservationResponse response = mapper.toResponse(testReservation);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(10L, response.getUserId());
        assertEquals("Alice", response.getUserName());
        assertEquals(20L, response.getParkingSpotId());
        assertEquals("Grand Central", response.getParkingName());
        assertEquals(15.0, response.getPrice());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToJpa() {
        ReservationJpaEntity entity = mapper.toJpaEntity(testReservation);

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

        Reservation domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals(15.0, domain.getPrice());
    }

    @Test
    @DisplayName("Null handling - Should return null when primary inputs are null")
    void shouldReturnNullWhenInputsAreNull() {
        assertNull(mapper.toDomainFromRequest(null));
        assertNull(mapper.toResponse(null));
        assertNull(mapper.toJpaEntity(null));
        assertNull(mapper.toDomain(null));
    }

    @Test
    @DisplayName("toResponse - Should handle null nested User and Spot")
    void shouldHandleNullNestedObjectsInResponse() {
        testReservation.setUser(null);
        testReservation.setParkingSpot(null);

        ReservationResponse response = mapper.toResponse(testReservation);

        assertNotNull(response);
        assertNull(response.getUserId());
        assertNull(response.getUserName());
        assertNull(response.getParkingSpotId());
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toResponse - Should handle null fields in nested User and Spot")
    void shouldHandleNullFieldsInNestedObjectsInResponse() {
        testUser.setId(null);
        testUser.setName(null);
        testParkingSpot.setId(null);
        testParking.setName(null);
        
        ReservationResponse response = mapper.toResponse(testReservation);

        assertNotNull(response);
        assertNull(response.getUserId());
        assertNull(response.getUserName());
        assertNull(response.getParkingSpotId());
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toResponse - Should handle null price by not setting it (keeping default 0.0)")
    void shouldHandleNullPriceInResponse() {
        testReservation.setPrice(null);
        ReservationResponse response = mapper.toResponse(testReservation);
        assertEquals(0.0, response.getPrice());
    }

    @Test
    @DisplayName("toResponse - Should handle null basePrice by returning 0.0")
    void shouldHandleNullBasePriceInResponse() {
        testReservation.setBasePrice(null);
        ReservationResponse response = mapper.toResponse(testReservation);
        assertEquals(0.0, response.getBasePrice());
    }

    @Test
    @DisplayName("calculateSanctionPrice - Should return 0.0 when sanctions list is null")
    void shouldHandleNullSanctionsInCalculation() {
        testReservation.setSanctions(null);
        assertEquals(0.0, mapper.calculateSanctionPrice(testReservation));
    }

    @Test
    @DisplayName("Internal: domainUserId - Should handle nulls via reflection")
    void shouldHandleNullsInInternalDomainUserId() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("domainUserId", Reservation.class);
        method.setAccessible(true);

        assertNull(method.invoke(mapper, (Reservation) null));
        
        testReservation.setUser(null);
        assertNull(method.invoke(mapper, testReservation));

        testUser.setId(null);
        testReservation.setUser(testUser);
        assertNull(method.invoke(mapper, testReservation));

        testUser.setId(55L);
        assertEquals(55L, method.invoke(mapper, testReservation));
    }

    @Test
    @DisplayName("Internal: domainUserName - Should handle nulls via reflection")
    void shouldHandleNullsInInternalDomainUserName() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("domainUserName", Reservation.class);
        method.setAccessible(true);

        assertNull(method.invoke(mapper, (Reservation) null));
        
        testReservation.setUser(null);
        assertNull(method.invoke(mapper, testReservation));

        testUser.setName(null);
        testReservation.setUser(testUser);
        assertNull(method.invoke(mapper, testReservation));

        testUser.setName("Alice");
        assertEquals("Alice", method.invoke(mapper, testReservation));
    }

    @Test
    @DisplayName("Internal: domainParkingSpotId - Should handle nulls via reflection")
    void shouldHandleNullsInInternalDomainParkingSpotId() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("domainParkingSpotId", Reservation.class);
        method.setAccessible(true);

        assertNull(method.invoke(mapper, (Reservation) null));
        
        testReservation.setParkingSpot(null);
        assertNull(method.invoke(mapper, testReservation));

        testParkingSpot.setId(null);
        testReservation.setParkingSpot(testParkingSpot);
        assertNull(method.invoke(mapper, testReservation));

        testParkingSpot.setId(20L);
        assertEquals(20L, method.invoke(mapper, testReservation));
    }

    @Test
    @DisplayName("Internal: domainParkingSpotParkingName - Should handle nulls via reflection")
    void shouldHandleNullsInInternalParkingName() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("domainParkingSpotParkingName", Reservation.class);
        method.setAccessible(true);

        assertNull(method.invoke(mapper, (Reservation) null));
        
        testReservation.setParkingSpot(null);
        assertNull(method.invoke(mapper, testReservation));

        testParkingSpot.setParking(null);
        testReservation.setParkingSpot(testParkingSpot);
        assertNull(method.invoke(mapper, testReservation));

        testParking.setName(null);
        testParkingSpot.setParking(testParking);
        assertNull(method.invoke(mapper, testReservation));

        testParking.setName("Grand Central");
        assertEquals("Grand Central", method.invoke(mapper, testReservation));
    }

    @Test
    @DisplayName("Internal: Mapping methods - Should handle null inputs via reflection")
    void shouldHandleNullInputsInInternalMappers() throws Exception {
        Method userMapper = mapper.getClass().getDeclaredMethod("reservationRequestToUser", ReservationRequest.class);
        userMapper.setAccessible(true);
        assertNull(userMapper.invoke(mapper, (ReservationRequest) null));

        Method spotMapper = mapper.getClass().getDeclaredMethod("reservationRequestToParkingSpot", ReservationRequest.class);
        spotMapper.setAccessible(true);
        assertNull(spotMapper.invoke(mapper, (ReservationRequest) null));
    }
}
