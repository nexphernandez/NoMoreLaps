package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ParkingResponse DTO.
 * Verifies that parking response data is correctly mapped and logical equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ParkingResponseTest {

    private ParkingResponse parkingResponse;
    private LocalDateTime timeRef;

    @BeforeEach
    void setUp() {
        parkingResponse = new ParkingResponse();
        timeRef = LocalDateTime.of(2026, 5, 20, 10, 0);
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        ParkingResponse idResponse = new ParkingResponse(88L);
        assertEquals(88L, idResponse.getId());
    }

    @Test
    @DisplayName("Full Constructor - Should correctly map all fields")
    void shouldInitializeWithFullConstructor() {
        ParkingResponse fullResponse = new ParkingResponse(1L, "Plaza Mayor 1", "Sol Parking", 40.4168, -3.7038, 
                                                            timeRef, timeRef.plusHours(12), timeRef.minusDays(1), 
                                                            4.50, 20.0, 30, 150);

        assertEquals(1L, fullResponse.getId());
        assertEquals("Plaza Mayor 1", fullResponse.getAddress());
        assertEquals("Sol Parking", fullResponse.getName());
        assertEquals(40.4168, fullResponse.getLatitude());
        assertEquals(-3.7038, fullResponse.getLongitude());
        assertEquals(timeRef, fullResponse.getOpeningTime());
        assertEquals(timeRef.plusHours(12), fullResponse.getClosingTime());
        assertEquals(timeRef.minusDays(1), fullResponse.getCreatedAt());
        assertEquals(4.50, fullResponse.getPricePerHour());
        assertEquals(20.0, fullResponse.getSanctionAmount());
        assertEquals(30, fullResponse.getSanctionIntervalInMinutes());
        assertEquals(150, fullResponse.getTotalSpots());
    }

    @Test
    @DisplayName("id - Should set and get the ID")
    void shouldSetAndGetId() {
        parkingResponse.setId(10L);
        assertEquals(10L, parkingResponse.getId());
    }

    @Test
    @DisplayName("address - Should set and get the address")
    void shouldSetAndGetAddress() {
        parkingResponse.setAddress("Calle de Alcalá, 1");
        assertEquals("Calle de Alcalá, 1", parkingResponse.getAddress());
    }

    @Test
    @DisplayName("name - Should set and get the name")
    void shouldSetAndGetName() {
        parkingResponse.setName("Retiro Parking");
        assertEquals("Retiro Parking", parkingResponse.getName());
    }

    @Test
    @DisplayName("latitude - Should set and get latitude")
    void shouldSetAndGetLatitude() {
        parkingResponse.setLatitude(40.4153);
        assertEquals(40.4153, parkingResponse.getLatitude());
    }

    @Test
    @DisplayName("longitude - Should set and get longitude")
    void shouldSetAndGetLongitude() {
        parkingResponse.setLongitude(-3.6844);
        assertEquals(-3.6844, parkingResponse.getLongitude());
    }

    @Test
    @DisplayName("openingTime - Should set and get opening time")
    void shouldSetAndGetOpeningTime() {
        parkingResponse.setOpeningTime(timeRef);
        assertEquals(timeRef, parkingResponse.getOpeningTime());
    }

    @Test
    @DisplayName("closingTime - Should set and get closing time")
    void shouldSetAndGetClosingTime() {
        parkingResponse.setClosingTime(timeRef.plusHours(8));
        assertEquals(timeRef.plusHours(8), parkingResponse.getClosingTime());
    }

    @Test
    @DisplayName("createdAt - Should set and get creation timestamp")
    void shouldSetAndGetCreatedAt() {
        parkingResponse.setCreatedAt(timeRef.minusMonths(1));
        assertEquals(timeRef.minusMonths(1), parkingResponse.getCreatedAt());
    }

    @Test
    @DisplayName("pricePerHour - Should set and get price")
    void shouldSetAndGetPricePerHour() {
        parkingResponse.setPricePerHour(5.0);
        assertEquals(5.0, parkingResponse.getPricePerHour());
    }

    @Test
    @DisplayName("sanctionAmount - Should set and get sanction price")
    void shouldSetAndGetSanctionAmount() {
        parkingResponse.setSanctionAmount(30.0);
        assertEquals(30.0, parkingResponse.getSanctionAmount());
    }

    @Test
    @DisplayName("sanctionIntervalInMinutes - Should set and get interval")
    void shouldSetAndGetSanctionInterval() {
        parkingResponse.setSanctionIntervalInMinutes(45);
        assertEquals(45, parkingResponse.getSanctionIntervalInMinutes());
    }

    @Test
    @DisplayName("totalSpots - Should set and get total capacity")
    void shouldSetAndGetTotalSpots() {
        parkingResponse.setTotalSpots(300);
        assertEquals(300, parkingResponse.getTotalSpots());
    }

    @Test
    @DisplayName("status - Should set and get status")
    void shouldSetAndGetStatus() {
        parkingResponse.setStatus("Closed");
        assertEquals("Closed", parkingResponse.getStatus());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        ParkingResponse first = new ParkingResponse(1L);
        ParkingResponse second = new ParkingResponse(1L);
        ParkingResponse third = new ParkingResponse(2L);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if IDs match");
        assertNotEquals(first, third, "Should not be equal if IDs differ");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, 12345, "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same ID")
    void shouldHaveConsistentHashCode() {
        ParkingResponse first = new ParkingResponse(1L);
        ParkingResponse second = new ParkingResponse(1L);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
