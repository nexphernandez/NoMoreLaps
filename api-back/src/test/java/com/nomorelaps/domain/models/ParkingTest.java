package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Parking domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class ParkingTest {

    private Parking testParking;

    @BeforeEach
    void setUp() {
        testParking = new Parking(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        Parking parking = new Parking();
        assertNull(parking.getId());
        assertNotNull(parking.getParkingSpots());
        assertNotNull(parking.getDynamicPrice());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        Parking parking = new Parking(5L);
        assertEquals(5L, parking.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Company company = new Company(10L);
        Set<ParkingSpot> spots = new HashSet<>();
        Set<DynamicPrice> prices = new HashSet<>();
        
        Parking parking = new Parking(1L, "Address", "Name", 40.0, -3.0, now, now, now, 2.5, company, 10.0, 15, spots, prices);
        
        assertEquals(1L, parking.getId());
        assertEquals("Address", parking.getAddress());
        assertEquals("Name", parking.getName());
        assertEquals(40.0, parking.getLatitude());
        assertEquals(-3.0, parking.getLongitude());
        assertEquals(now, parking.getOpeningTime());
        assertEquals(now, parking.getClosingTime());
        assertEquals(now, parking.getCreatedAt());
        assertEquals(2.5, parking.getPricePerHour());
        assertEquals(company, parking.getCompany());
        assertEquals(10.0, parking.getSanctionAmount());
        assertEquals(15, parking.getSanctionIntervalInMinutes());
        assertEquals(spots, parking.getParkingSpots());
        assertEquals(prices, parking.getDynamicPrice());
    }


    @Test
    @DisplayName("Getter/Setter - Address: Should preserve street string")
    void shouldSetAndGetAddress() {
        testParking.setAddress("Calle Principal 1");
        assertEquals("Calle Principal 1", testParking.getAddress());
    }

    @Test
    @DisplayName("Getter/Setter - Name: Should preserve commercial title")
    void shouldSetAndGetName() {
        testParking.setName("Parking Centro");
        assertEquals("Parking Centro", testParking.getName());
    }

    @Test
    @DisplayName("Getter/Setter - Latitude: Should preserve double coordinate")
    void shouldSetAndGetLatitude() {
        testParking.setLatitude(40.4168);
        assertEquals(40.4168, testParking.getLatitude());
    }

    @Test
    @DisplayName("Getter/Setter - Longitude: Should preserve double coordinate")
    void shouldSetAndGetLongitude() {
        testParking.setLongitude(-3.7038);
        assertEquals(-3.7038, testParking.getLongitude());
    }

    @Test
    @DisplayName("Getter/Setter - OpeningTime: Should preserve timestamp")
    void shouldSetAndGetOpeningTime() {
        LocalDateTime opening = LocalDateTime.now();
        testParking.setOpeningTime(opening);
        assertEquals(opening, testParking.getOpeningTime());
    }

    @Test
    @DisplayName("Getter/Setter - ClosingTime: Should preserve timestamp")
    void shouldSetAndGetClosingTime() {
        LocalDateTime closing = LocalDateTime.now();
        testParking.setClosingTime(closing);
        assertEquals(closing, testParking.getClosingTime());
    }

    @Test
    @DisplayName("Getter/Setter - CreatedAt: Should preserve timestamp")
    void shouldSetAndGetCreatedAt() {
        LocalDateTime created = LocalDateTime.now();
        testParking.setCreatedAt(created);
        assertEquals(created, testParking.getCreatedAt());
    }

    @Test
    @DisplayName("Getter/Setter - PricePerHour: Should preserve double value")
    void shouldSetAndGetPricePerHour() {
        testParking.setPricePerHour(3.5);
        assertEquals(3.5, testParking.getPricePerHour());
    }

    @Test
    @DisplayName("Getter/Setter - Company: Should preserve relationship")
    void shouldSetAndGetCompany() {
        Company company = new Company(1L);
        testParking.setCompany(company);
        assertEquals(company, testParking.getCompany());
    }

    @Test
    @DisplayName("Getter/Setter - SanctionAmount: Should preserve penalty value")
    void shouldSetAndGetSanctionAmount() {
        testParking.setSanctionAmount(25.0);
        assertEquals(25.0, testParking.getSanctionAmount());
    }

    @Test
    @DisplayName("Getter/Setter - SanctionIntervalInMinutes: Should preserve integer value")
    void shouldSetAndGetSanctionInterval() {
        testParking.setSanctionIntervalInMinutes(20);
        assertEquals(20, testParking.getSanctionIntervalInMinutes());
    }

    @Test
    @DisplayName("Getter/Setter - TotalSpots: Should preserve integer value")
    void shouldSetAndGetTotalSpots() {
        testParking.setTotalSpots(50);
        assertEquals(50, testParking.getTotalSpots());
    }

    @Test
    @DisplayName("Getter/Setter - Sets: Should preserve parking spots and dynamic prices")
    void shouldSetAndGetSets() {
        Set<ParkingSpot> spots = new HashSet<>();
        Set<DynamicPrice> prices = new HashSet<>();
        testParking.setParkingSpots(spots);
        testParking.setDynamicPrice(prices);
        assertEquals(spots, testParking.getParkingSpots());
        assertEquals(prices, testParking.getDynamicPrice());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testParking, testParking);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testParking, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testParking, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        Parking other = new Parking(1L);
        assertEquals(testParking, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        Parking other = new Parking(2L);
        assertNotEquals(testParking, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        Parking p1 = new Parking();
        Parking p2 = new Parking();
        assertEquals(p1, p2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        Parking p1 = new Parking(1L);
        Parking p2 = new Parking();
        assertNotEquals(p1, p2);
        assertNotEquals(p2, p1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        Parking other = new Parking(1L);
        assertEquals(testParking.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        Parking other = new Parking(2L);
        assertNotEquals(testParking.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        Parking p1 = new Parking();
        Parking p2 = new Parking();
        assertEquals(p1.hashCode(), p2.hashCode());
    }
}
