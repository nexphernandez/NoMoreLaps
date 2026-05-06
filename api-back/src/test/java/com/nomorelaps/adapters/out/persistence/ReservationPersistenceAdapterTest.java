package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IUserPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;

@SpringBootTest
@Transactional
class ReservationPersistenceAdapterTest {

    @Autowired
    private IReservationPersistenceAdapter reservationAdapter;

    @Autowired
    private IParkingPersistenceAdapter parkingAdapter;

    @Autowired
    private IParkingSpotPersistenceAdapter spotAdapter;

    @Autowired
    private IUserPersistenceAdapter userAdapter;

    private User testUser;
    private ParkingSpot testSpot;
    private Parking testParking;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setEmail("integration@test.com");
        user.setName("Integration Test");
        testUser = userAdapter.save(user);
 
        Parking parking = new Parking();
        parking.setName("Test Parking");
        parking.setAddress("Test Address");
        testParking = parkingAdapter.save(parking);
 
        ParkingSpot spot = new ParkingSpot();
        spot.setNumber(999);
        spot.setParking(testParking);
        testSpot = spotAdapter.save(spot);
    }

    @Test
    @DisplayName("Should save and find reservation")
    void shouldSaveAndFindReservation() {
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now().plusDays(1));
        reservation.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
        reservation.setUser(testUser);
        reservation.setParkingSpot(testSpot);
        reservation.setState("PENDING");

        Reservation saved = reservationAdapter.save(reservation);
        assertNotNull(saved.getId());

        Reservation found = reservationAdapter.findById(saved.getId()).orElse(null);

        assertNotNull(found);
        assertEquals(testUser.getId(), found.getUser().getId());
        assertEquals(testSpot.getId(), found.getParkingSpot().getId());
    }

    @Test
    @DisplayName("Should detect overlapping reservations")
    void shouldDetectOverlaps() {
        LocalDateTime start = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Reservation r1 = new Reservation();
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setUser(testUser);
        r1.setParkingSpot(testSpot);
        r1.setState("ACTIVE");
        reservationAdapter.save(r1);

        List<Reservation> all = reservationAdapter.findByParkingSpotId(testSpot.getId());
        assertFalse(all.isEmpty(), "Reservation should be in database");
        assertEquals("ACTIVE", all.get(0).getState(), "State should be ACTIVE");

        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start, end), "Exact same time should overlap");
        
        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start.plusMinutes(30), end.plusHours(1)), "Partial start overlap should overlap");

        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start.minusHours(1), start.plusMinutes(30)), "Partial end overlap should overlap");

        assertFalse(reservationAdapter.hasOverlappingReservations(testSpot.getId(), end.plusHours(1), end.plusHours(2)), "Non-overlapping times should not overlap");
    }

    @Test
    @DisplayName("Should handle null associations in toEntity (save)")
    void shouldHandleNullAssociationsInSave() {
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now().plusDays(5));
        reservation.setEndTime(LocalDateTime.now().plusDays(5).plusHours(1));
        reservation.setUser(null);
        reservation.setParkingSpot(null);
        reservation.setState("PENDING");

        Reservation saved = reservationAdapter.save(reservation);
        assertNotNull(saved.getId());
        
        Reservation found = reservationAdapter.findById(saved.getId()).orElse(null);
        assertNotNull(found);
        assertNull(found.getUser());
        assertNull(found.getParkingSpot());
    }

    @Test
    @DisplayName("Should handle partial null associations in toEntity (save)")
    void shouldHandlePartialNullAssociationsInSave() {
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now().plusDays(6));
        reservation.setEndTime(LocalDateTime.now().plusDays(6).plusHours(1));
        
        User userNoId = new User();
        userNoId.setId(null);
        reservation.setUser(userNoId);

        ParkingSpot spotNoId = new ParkingSpot();
        spotNoId.setId(null);
        reservation.setParkingSpot(spotNoId);

        Reservation saved = reservationAdapter.save(reservation);
        assertNotNull(saved.getId());
    }

    @Test
    @DisplayName("Should cover hasOverlappingReservationsExcluding")
    void shouldCoverOverlapExcluding() {
        LocalDateTime start = LocalDateTime.of(2026, 7, 1, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Reservation r1 = new Reservation();
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setUser(testUser);
        r1.setParkingSpot(testSpot);
        r1.setState("ACTIVE");
        Reservation saved = reservationAdapter.save(r1);

        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start, end));
        
        assertFalse(reservationAdapter.hasOverlappingReservationsExcluding(testSpot.getId(), start, end, saved.getId()));
    }

    @Test
    @DisplayName("Should handle null parking in spot in toDomain")
    void shouldHandleNullParkingInSpotInToDomain() {
        ParkingSpot spot = new ParkingSpot();
        spot.setNumber(888);
        spot.setParking(null);
        ParkingSpot savedSpot = spotAdapter.save(spot);

        Reservation r = new Reservation();
        r.setStartTime(LocalDateTime.now().plusDays(10));
        r.setEndTime(LocalDateTime.now().plusDays(10).plusHours(1));
        r.setParkingSpot(savedSpot);
        r.setState("ACTIVE");
        
        Reservation saved = reservationAdapter.save(r);
        Reservation found = reservationAdapter.findById(saved.getId()).orElse(null);
        
        assertNotNull(found.getParkingSpot());
        assertNull(found.getParkingSpot().getParking());
    }

    @Test
    @DisplayName("findByUserId - Should return list of reservations")
    void shouldFindByUserId() {
        Reservation r = new Reservation();
        r.setStartTime(LocalDateTime.now().plusDays(11));
        r.setEndTime(LocalDateTime.now().plusDays(11).plusHours(1));
        r.setUser(testUser);
        r.setState("ACTIVE");
        reservationAdapter.save(r);

        List<Reservation> found = reservationAdapter.findByUserId(testUser.getId());
        assertFalse(found.isEmpty());
        assertTrue(found.stream().anyMatch(res -> res.getUser().getId().equals(testUser.getId())));
    }

    @Test
    @DisplayName("findByParkingId - Should return list of reservations")
    void shouldFindByParkingId() {
        Parking parking = new Parking();
        parking.setName("Search Parking");
        Parking savedParking = parkingAdapter.save(parking);

        ParkingSpot spot = new ParkingSpot();
        spot.setNumber(777);
        spot.setParking(savedParking);
        ParkingSpot savedSpot = spotAdapter.save(spot);

        Reservation r = new Reservation();
        r.setStartTime(LocalDateTime.now().plusDays(15));
        r.setEndTime(LocalDateTime.now().plusDays(15).plusHours(1));
        r.setParkingSpot(savedSpot);
        r.setState("ACTIVE");
        Reservation savedRes = reservationAdapter.save(r);

        assertNotNull(savedRes.getId());

        List<Reservation> found = reservationAdapter.findByParkingId(savedParking.getId());
        assertFalse(found.isEmpty(), "Should find the reservation for the new parking");
        assertTrue(found.stream().anyMatch(res -> res.getId().equals(savedRes.getId())));
    }

    @Test
    @DisplayName("toDomain - Should map full parking data when available")
    void shouldMapFullParkingDataInToDomain() {

        ParkingJpaEntity pEntity = new ParkingJpaEntity();
        pEntity.setId(5L);
        pEntity.setName("Full Mapping Parking");
        pEntity.setAddress("Map Street 1");
        pEntity.setLatitude(10.0);
        pEntity.setLongitude(20.0);
        pEntity.setPricePerHour(3.5);
        pEntity.setSanctionAmount(50.0);
        pEntity.setSanctionIntervalInMinutes(30);

        ParkingSpotJpaEntity sEntity = new ParkingSpotJpaEntity();
        sEntity.setId(10L);
        sEntity.setParking(pEntity);

        ReservationJpaEntity rEntity = new ReservationJpaEntity();
        rEntity.setId(1L);
        rEntity.setParkingSpot(sEntity);

        ReservationPersistenceAdapter impl = (ReservationPersistenceAdapter) reservationAdapter;
        Reservation domain = impl.toDomain(rEntity);

        assertNotNull(domain.getParkingSpot());
        assertNotNull(domain.getParkingSpot().getParking());
        assertEquals("Full Mapping Parking", domain.getParkingSpot().getParking().getName());
        assertEquals(3.5, domain.getParkingSpot().getParking().getPricePerHour());
    }

    @Test
    @DisplayName("hasOverlappingReservationsExcluding - Should return true if ANOTHER overlapping reservation exists")
    void shouldReturnTrueWhenAnotherOverlapExists() {
        LocalDateTime start = LocalDateTime.of(2026, 8, 1, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Reservation r1 = new Reservation();
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setUser(testUser);
        r1.setParkingSpot(testSpot);
        r1.setState("ACTIVE");
        Reservation saved1 = reservationAdapter.save(r1);

        Reservation r2 = new Reservation();
        r2.setStartTime(start.plusMinutes(30));
        r2.setEndTime(end.plusMinutes(30));
        r2.setUser(testUser);
        r2.setParkingSpot(testSpot);
        r2.setState("ACTIVE");
        reservationAdapter.save(r2);


        assertTrue(reservationAdapter.hasOverlappingReservationsExcluding(testSpot.getId(), start, end, saved1.getId()), 
            "Should return true because r2 still overlaps even if r1 is excluded");
    }
}


