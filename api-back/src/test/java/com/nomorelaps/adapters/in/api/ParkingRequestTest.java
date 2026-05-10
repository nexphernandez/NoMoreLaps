package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for ParkingRequest DTO.
 * Verifies that parking configuration data is correctly handled.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class ParkingRequestTest {

    private ParkingRequest parkingRequest;
    private LocalDateTime timeRef;

    @BeforeEach
    void setUp() {
        parkingRequest = new ParkingRequest();
        timeRef = LocalDateTime.of(2026, 5, 20, 8, 0);
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithFullConstructor() {
        ParkingRequest fullRequest = new ParkingRequest("123 Main St", "Downtown Parking", 40.4168, -3.7038, 
                                                        timeRef, timeRef.plusHours(12), 3.50, 100L, 25.0, 60);

        assertEquals("123 Main St", fullRequest.getAddress());
        assertEquals("Downtown Parking", fullRequest.getName());
        assertEquals(40.4168, fullRequest.getLatitude());
        assertEquals(-3.7038, fullRequest.getLongitude());
        assertEquals(timeRef, fullRequest.getOpeningTime());
        assertEquals(timeRef.plusHours(12), fullRequest.getClosingTime());
        assertEquals(3.50, fullRequest.getPricePerHour());
        assertEquals(100L, fullRequest.getCompanyId());
        assertEquals(25.0, fullRequest.getSanctionAmount());
        assertEquals(60, fullRequest.getSanctionIntervalInMinutes());
    }

    @Test
    @DisplayName("address - Should set and get the address")
    void shouldSetAndGetAddress() {
        parkingRequest.setAddress("Calle Mayor 1");
        assertEquals("Calle Mayor 1", parkingRequest.getAddress());
    }

    @Test
    @DisplayName("name - Should set and get the name")
    void shouldSetAndGetName() {
        parkingRequest.setName("Parking Sol");
        assertEquals("Parking Sol", parkingRequest.getName());
    }

    @Test
    @DisplayName("latitude - Should set and get latitude")
    void shouldSetAndGetLatitude() {
        parkingRequest.setLatitude(41.3851);
        assertEquals(41.3851, parkingRequest.getLatitude());
    }

    @Test
    @DisplayName("longitude - Should set and get longitude")
    void shouldSetAndGetLongitude() {
        parkingRequest.setLongitude(2.1734);
        assertEquals(2.1734, parkingRequest.getLongitude());
    }

    @Test
    @DisplayName("openingTime - Should set and get opening time")
    void shouldSetAndGetOpeningTime() {
        parkingRequest.setOpeningTime(timeRef);
        assertEquals(timeRef, parkingRequest.getOpeningTime());
    }

    @Test
    @DisplayName("closingTime - Should set and get closing time")
    void shouldSetAndGetClosingTime() {
        parkingRequest.setClosingTime(timeRef.plusHours(8));
        assertEquals(timeRef.plusHours(8), parkingRequest.getClosingTime());
    }

    @Test
    @DisplayName("pricePerHour - Should set and get price")
    void shouldSetAndGetPricePerHour() {
        parkingRequest.setPricePerHour(4.25);
        assertEquals(4.25, parkingRequest.getPricePerHour());
    }

    @Test
    @DisplayName("companyId - Should set and get company ID")
    void shouldSetAndGetCompanyId() {
        parkingRequest.setCompanyId(50L);
        assertEquals(50L, parkingRequest.getCompanyId());
    }

    @Test
    @DisplayName("sanctionAmount - Should set and get sanction price")
    void shouldSetAndGetSanctionAmount() {
        parkingRequest.setSanctionAmount(10.0);
        assertEquals(10.0, parkingRequest.getSanctionAmount());
    }

    @Test
    @DisplayName("sanctionIntervalInMinutes - Should set and get interval")
    void shouldSetAndGetSanctionInterval() {
        parkingRequest.setSanctionIntervalInMinutes(15);
        assertEquals(15, parkingRequest.getSanctionIntervalInMinutes());
    }

    @Test
    @DisplayName("totalSpots - Should set and get total capacity")
    void shouldSetAndGetTotalSpots() {
        parkingRequest.setTotalSpots(200);
        assertEquals(200, parkingRequest.getTotalSpots());
    }
}
