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

    @BeforeEach
    void setUp() {
        // Create user
        User user = new User();
        user.setEmail("integration@test.com");
        user.setName("Integration Test");
        testUser = userAdapter.save(user);

        // Create parking
        Parking parking = new Parking();
        parking.setName("Test Parking");
        parking.setAddress("Test Address");
        Parking savedParking = parkingAdapter.save(parking);

        // Create spot
        ParkingSpot spot = new ParkingSpot();
        spot.setNumber(999);
        spot.setParking(savedParking);
        testSpot = spotAdapter.save(spot);
    }

    @Test
    @DisplayName("Should save and find reservation")
    void shouldSaveAndFindReservation() {
        // Arrange
        Reservation reservation = new Reservation();
        reservation.setStartTime(LocalDateTime.now().plusDays(1));
        reservation.setEndTime(LocalDateTime.now().plusDays(1).plusHours(2));
        reservation.setUser(testUser);
        reservation.setParkingSpot(testSpot);
        reservation.setState("PENDING");

        // Act
        Reservation saved = reservationAdapter.save(reservation);
        assertNotNull(saved.getId());

        Reservation found = reservationAdapter.findById(saved.getId()).orElse(null);

        // Assert
        assertNotNull(found);
        assertEquals(testUser.getId(), found.getUser().getId());
        assertEquals(testSpot.getId(), found.getParkingSpot().getId());
    }

    @Test
    @DisplayName("Should detect overlapping reservations")
    void shouldDetectOverlaps() {
        // Arrange
        LocalDateTime start = LocalDateTime.of(2026, 6, 1, 10, 0);
        LocalDateTime end = start.plusHours(2);

        Reservation r1 = new Reservation();
        r1.setStartTime(start);
        r1.setEndTime(end);
        r1.setUser(testUser);
        r1.setParkingSpot(testSpot);
        r1.setState("ACTIVE");
        reservationAdapter.save(r1);

        // Verify it was saved correctly
        List<Reservation> all = reservationAdapter.findByParkingSpotId(testSpot.getId());
        assertFalse(all.isEmpty(), "Reservation should be in database");
        assertEquals("ACTIVE", all.get(0).getState(), "State should be ACTIVE");

        // Act & Assert
        // Case 1: Exact same time
        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start, end), "Exact same time should overlap");
        
        // Case 2: Starts during r1
        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start.plusMinutes(30), end.plusHours(1)), "Partial start overlap should overlap");

        // Case 3: Ends during r1
        assertTrue(reservationAdapter.hasOverlappingReservations(testSpot.getId(), start.minusHours(1), start.plusMinutes(30)), "Partial end overlap should overlap");

        // Case 4: No overlap
        assertFalse(reservationAdapter.hasOverlappingReservations(testSpot.getId(), end.plusHours(1), end.plusHours(2)), "Non-overlapping times should not overlap");
    }
}
