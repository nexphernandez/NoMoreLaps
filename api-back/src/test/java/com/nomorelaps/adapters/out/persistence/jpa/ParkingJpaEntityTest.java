package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for ParkingJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class ParkingJpaEntityTest {

    private ParkingJpaEntity testParking;

    @BeforeEach
    void setUp() {
        testParking = new ParkingJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new ParkingJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        ParkingJpaEntity entity = new ParkingJpaEntity(100L);
        assertEquals(100L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        CompanyJpaEntity company = new CompanyJpaEntity(10L);
        Set<ParkingSpotJpaEntity> spots = new HashSet<>();
        Set<DynamicPriceJpaEntity> prices = new HashSet<>();

        ParkingJpaEntity entity = new ParkingJpaEntity(5L, "Street 1", "Main Parking", 40.0, -3.0, 
            now, now.plusHours(12), now, 2.5, company, 10.0, 15, spots, prices);

        assertEquals(5L, entity.getId());
        assertEquals("Street 1", entity.getAddress());
        assertEquals("Main Parking", entity.getName());
        assertEquals(40.0, entity.getLatitude());
        assertEquals(-3.0, entity.getLongitude());
        assertEquals(now, entity.getOpeningTime());
        assertEquals(2.5, entity.getPricePerHour());
        assertEquals(company, entity.getCompany());
        assertEquals(spots, entity.getParkingSpots());
        assertEquals(prices, entity.getDynamicPrice());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testParking.setId(1L);
        assertEquals(1L, testParking.getId());
        
        testParking.setName("Central Park");
        assertEquals("Central Park", testParking.getName());
        
        testParking.setAddress("Avenue 5");
        assertEquals("Avenue 5", testParking.getAddress());
        
        testParking.setLatitude(45.0);
        assertEquals(45.0, testParking.getLatitude());
        
        testParking.setLongitude(-2.0);
        assertEquals(-2.0, testParking.getLongitude());
        
        testParking.setPricePerHour(3.0);
        assertEquals(3.0, testParking.getPricePerHour());
        
        testParking.setSanctionAmount(20.0);
        assertEquals(20.0, testParking.getSanctionAmount());
        
        testParking.setSanctionIntervalInMinutes(30);
        assertEquals(30, testParking.getSanctionIntervalInMinutes());
        
        testParking.setTotalSpots(50);
        assertEquals(50, testParking.getTotalSpots());
        
        LocalDateTime now = LocalDateTime.now();
        testParking.setOpeningTime(now);
        assertEquals(now, testParking.getOpeningTime());
        
        testParking.setClosingTime(now.plusHours(8));
        assertEquals(now.plusHours(8), testParking.getClosingTime());
    }

    @Test
    @DisplayName("Relationships - Should update Company, Spots and Prices")
    void shouldSetRelationships() {
        CompanyJpaEntity company = new CompanyJpaEntity(10L);
        Set<ParkingSpotJpaEntity> spots = new HashSet<>();
        Set<DynamicPriceJpaEntity> prices = new HashSet<>();

        testParking.setCompany(company);
        testParking.setParkingSpots(spots);
        testParking.setDynamicPrice(prices);

        assertEquals(company, testParking.getCompany());
        assertEquals(spots, testParking.getParkingSpots());
        assertEquals(prices, testParking.getDynamicPrice());
    }

    @Test
    @DisplayName("onCreate - Should set creation timestamp if null")
    void shouldSetTimestampOnCreateIfNull() {
        assertNull(testParking.getCreatedAt());
        testParking.onCreate();
        assertNotNull(testParking.getCreatedAt());
    }

    @Test
    @DisplayName("onCreate - Should not overwrite creation timestamp if already set")
    void shouldNotOverwriteTimestampOnCreate() {
        LocalDateTime past = LocalDateTime.now().minusDays(1);
        testParking.setCreatedAt(past);
        testParking.onCreate();
        assertEquals(past, testParking.getCreatedAt());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        ParkingJpaEntity p1 = new ParkingJpaEntity(1L);
        ParkingJpaEntity p2 = new ParkingJpaEntity(1L);
        ParkingJpaEntity p3 = new ParkingJpaEntity(2L);

        assertEquals(p1, p1);
        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        ParkingJpaEntity p1 = new ParkingJpaEntity(null);
        ParkingJpaEntity p2 = new ParkingJpaEntity(null);
        assertEquals(p1, p2);
    }
}
