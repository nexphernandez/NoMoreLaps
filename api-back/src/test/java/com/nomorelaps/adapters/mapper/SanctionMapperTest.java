package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

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

class SanctionMapperTest {

    private final SanctionMapper mapper = Mappers.getMapper(SanctionMapper.class);


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

    @Test
    @DisplayName("toResponse - Should handle nested nulls")
    void shouldHandleNestedNullsInResponse() {
        Sanction s = new Sanction(1L);
        
        s.setUser(null);
        SanctionResponse r1 = mapper.toResponse(s);
        assertNull(r1.getUserId());
        assertNull(r1.getUserName());

        User u = new User();
        u.setId(null);
        u.setName(null);
        s.setUser(u);
        SanctionResponse r2 = mapper.toResponse(s);
        assertNull(r2.getUserId());
        assertNull(r2.getUserName());

        s.setReservation(null);
        SanctionResponse r3 = mapper.toResponse(s);
        assertNull(r3.getParkingName());

        Reservation res = new Reservation();
        res.setParkingSpot(null);
        s.setReservation(res);
        SanctionResponse r4 = mapper.toResponse(s);
        assertNull(r4.getParkingName());

        ParkingSpot spot = new ParkingSpot();
        spot.setParking(null);
        res.setParkingSpot(spot);
        SanctionResponse r5 = mapper.toResponse(s);
        assertNull(r5.getParkingName());

        Parking p = new Parking();
        p.setName(null);
        spot.setParking(p);
        SanctionResponse r6 = mapper.toResponse(s);
        assertNull(r6.getParkingName());
    }

    @Test
    @DisplayName("Internal methods null checks - Reflection")
    void shouldHandleNullsInInternalMethods() throws Exception {
        Object impl = mapper;

        Method m1 = impl.getClass().getDeclaredMethod("domainUserId", Sanction.class);
        m1.setAccessible(true);
        assertNull(m1.invoke(impl, (Sanction) null));
        Sanction s = new Sanction();
        s.setUser(null);
        assertNull(m1.invoke(impl, s));
        User u = new User();
        u.setId(null);
        s.setUser(u);
        assertNull(m1.invoke(impl, s));
        
        u.setId(55L);
        assertEquals(55L, m1.invoke(impl, s));

        Method m2 = impl.getClass().getDeclaredMethod("domainUserName", Sanction.class);
        m2.setAccessible(true);
        assertNull(m2.invoke(impl, (Sanction) null));
        s.setUser(null);
        assertNull(m2.invoke(impl, s));
        u.setName(null);
        s.setUser(u);
        assertNull(m2.invoke(impl, s));
        
        u.setName("Bob");
        assertEquals("Bob", m2.invoke(impl, s));

        Method m3 = impl.getClass().getDeclaredMethod("domainReservationParkingSpotParkingName", Sanction.class);
        m3.setAccessible(true);
        assertNull(m3.invoke(impl, (Sanction) null));
        s.setReservation(null);
        assertNull(m3.invoke(impl, s));
        
        Reservation res = new Reservation();
        res.setParkingSpot(null);
        s.setReservation(res);
        assertNull(m3.invoke(impl, s));

        ParkingSpot spot = new ParkingSpot();
        spot.setParking(null);
        res.setParkingSpot(spot);
        assertNull(m3.invoke(impl, s));

        Parking p = new Parking();
        p.setName(null);
        spot.setParking(p);
        assertNull(m3.invoke(impl, s));

        p.setName("Parking Lot");
        assertEquals("Parking Lot", m3.invoke(impl, s));
    }
}
