package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

/**
 * Unit tests for DynamicPriceJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class DynamicPriceJpaEntityTest {

    private DynamicPriceJpaEntity testPrice;

    @BeforeEach
    void setUp() {
        testPrice = new DynamicPriceJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new DynamicPriceJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        DynamicPriceJpaEntity entity = new DynamicPriceJpaEntity(10L);
        assertEquals(10L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        ParkingJpaEntity parking = new ParkingJpaEntity(5L);

        DynamicPriceJpaEntity entity = new DynamicPriceJpaEntity(1L, 1, "10:00", "12:00", 
            1.0, 5.0, now, parking);

        assertEquals(1L, entity.getId());
        assertEquals(1, entity.getDayOfWeek());
        assertEquals("10:00", entity.getStartHour());
        assertEquals("12:00", entity.getEndHour());
        assertEquals(1.0, entity.getMinPrice());
        assertEquals(5.0, entity.getMaxPrice());
        assertEquals(now, entity.getCreateAt());
        assertEquals(parking, entity.getParking());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testPrice.setId(1L);
        assertEquals(1L, testPrice.getId());
        
        testPrice.setDayOfWeek(5);
        assertEquals(5, testPrice.getDayOfWeek());
        
        testPrice.setStartHour("08:00");
        assertEquals("08:00", testPrice.getStartHour());
        
        testPrice.setEndHour("20:00");
        assertEquals("20:00", testPrice.getEndHour());
        
        testPrice.setMinPrice(2.0);
        assertEquals(2.0, testPrice.getMinPrice());
        
        testPrice.setMaxPrice(4.0);
        assertEquals(4.0, testPrice.getMaxPrice());
        
        LocalDateTime now = LocalDateTime.now();
        testPrice.setCreateAt(now);
        assertEquals(now, testPrice.getCreateAt());
    }

    @Test
    @DisplayName("Relationships - Should update Parking")
    void shouldSetRelationships() {
        ParkingJpaEntity parking = new ParkingJpaEntity(5L);
        testPrice.setParking(parking);
        assertEquals(parking, testPrice.getParking());
    }

    @Test
    @DisplayName("onCreate - Should set creation date if null")
    void shouldSetTimestampOnCreate() {
        assertNull(testPrice.getCreateAt());
        testPrice.onCreate();
        assertNotNull(testPrice.getCreateAt());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        DynamicPriceJpaEntity d1 = new DynamicPriceJpaEntity(1L);
        DynamicPriceJpaEntity d2 = new DynamicPriceJpaEntity(1L);
        DynamicPriceJpaEntity d3 = new DynamicPriceJpaEntity(2L);

        assertEquals(d1, d1);
        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
        assertNotEquals(d1, d3);
        assertNotEquals(d1, null);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        DynamicPriceJpaEntity d1 = new DynamicPriceJpaEntity(null);
        DynamicPriceJpaEntity d2 = new DynamicPriceJpaEntity(null);
        assertEquals(d1, d2);
    }
}
