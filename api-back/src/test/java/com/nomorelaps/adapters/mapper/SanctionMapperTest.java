package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import java.lang.reflect.Method;

import com.nomorelaps.adapters.in.api.SanctionRequest;
import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for SanctionMapper.
 * Verifies mapping between Domain models, Requests, Responses, and JPA Entities.
 * Adheres to granular branch testing and robust null-safety validation.
 */
class SanctionMapperTest {

    private final SanctionMapper mapper = Mappers.getMapper(SanctionMapper.class);
    private Sanction testSanction;
    private User testUser;
    private Reservation testReservation;
    private ParkingSpot testParkingSpot;
    private Parking testParking;

    @BeforeEach
    void setUp() {
        testSanction = new Sanction(1L);
        testUser = new User();
        testReservation = new Reservation();
        testParkingSpot = new ParkingSpot();
        testParking = new Parking();
    }

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
        testSanction.setAmount(25.0);

        SanctionResponse response = mapper.toResponse(testSanction);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(25.0, response.getAmount());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to jpa")
    void shouldMapDomainToJpa() {
        testSanction.setReason("Damage");

        SanctionJpaEntity entity = mapper.toJpaEntity(testSanction);

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

    @Test
    @DisplayName("toResponse - Should return null fields when User is null")
    void shouldHandleNullUserInResponse() {
        testSanction.setUser(null);
        SanctionResponse response = mapper.toResponse(testSanction);
        assertNull(response.getUserId());
        assertNull(response.getUserName());
    }

    @Test
    @DisplayName("toResponse - Should return null fields when User fields are null")
    void shouldHandleNullUserFieldsInResponse() {
        testUser.setId(null);
        testUser.setName(null);
        testSanction.setUser(testUser);
        SanctionResponse response = mapper.toResponse(testSanction);
        assertNull(response.getUserId());
        assertNull(response.getUserName());
    }

    @Test
    @DisplayName("toResponse - Should return null parkingName when Reservation is null")
    void shouldHandleNullReservationInResponse() {
        testSanction.setReservation(null);
        SanctionResponse response = mapper.toResponse(testSanction);
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toResponse - Should return null parkingName when ParkingSpot is null")
    void shouldHandleNullParkingSpotInResponse() {
        testSanction.setReservation(testReservation);
        testReservation.setParkingSpot(null);
        SanctionResponse response = mapper.toResponse(testSanction);
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("toResponse - Should return null parkingName when Parking is null")
    void shouldHandleNullParkingInResponse() {
        testReservation.setParkingSpot(testParkingSpot);
        testParkingSpot.setParking(null);
        testSanction.setReservation(testReservation);
        SanctionResponse response = mapper.toResponse(testSanction);
        assertNull(response.getParkingName());
    }

    @Test
    @DisplayName("Internal: domainUserId - Should handle nulls via reflection")
    void shouldHandleNullsInInternalDomainUserId() throws Exception {
        Method domainUserIdMethod = mapper.getClass().getDeclaredMethod("domainUserId", Sanction.class);
        domainUserIdMethod.setAccessible(true);

        assertNull(domainUserIdMethod.invoke(mapper, (Sanction) null));
        
        testSanction.setUser(null);
        assertNull(domainUserIdMethod.invoke(mapper, testSanction));
        
        testUser.setId(null);
        testSanction.setUser(testUser);
        assertNull(domainUserIdMethod.invoke(mapper, testSanction));

        testUser.setId(55L);
        assertEquals(55L, domainUserIdMethod.invoke(mapper, testSanction));
    }

    @Test
    @DisplayName("Internal: domainUserName - Should handle nulls via reflection")
    void shouldHandleNullsInInternalDomainUserName() throws Exception {
        Method domainUserNameMethod = mapper.getClass().getDeclaredMethod("domainUserName", Sanction.class);
        domainUserNameMethod.setAccessible(true);

        assertNull(domainUserNameMethod.invoke(mapper, (Sanction) null));
        
        testSanction.setUser(null);
        assertNull(domainUserNameMethod.invoke(mapper, testSanction));
        
        testUser.setName(null);
        testSanction.setUser(testUser);
        assertNull(domainUserNameMethod.invoke(mapper, testSanction));

        testUser.setName("Bob");
        assertEquals("Bob", domainUserNameMethod.invoke(mapper, testSanction));
    }

    @Test
    @DisplayName("Internal: domainReservationParkingSpotParkingName - Should handle nulls via reflection")
    void shouldHandleNullsInInternalParkingName() throws Exception {
        Method parkingNameMethod = mapper.getClass().getDeclaredMethod("domainReservationParkingSpotParkingName", Sanction.class);
        parkingNameMethod.setAccessible(true);

        assertNull(parkingNameMethod.invoke(mapper, (Sanction) null));
        
        testSanction.setReservation(null);
        assertNull(parkingNameMethod.invoke(mapper, testSanction));
        
        testReservation.setParkingSpot(null);
        testSanction.setReservation(testReservation);
        assertNull(parkingNameMethod.invoke(mapper, testSanction));

        testParkingSpot.setParking(null);
        testReservation.setParkingSpot(testParkingSpot);
        assertNull(parkingNameMethod.invoke(mapper, testSanction));

        testParking.setName(null);
        testParkingSpot.setParking(testParking);
        assertNull(parkingNameMethod.invoke(mapper, testSanction));

        testParking.setName("Grand Central");
        assertEquals("Grand Central", parkingNameMethod.invoke(mapper, testSanction));
    }
}
