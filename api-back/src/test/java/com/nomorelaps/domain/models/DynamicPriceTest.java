package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DynamicPrice domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class DynamicPriceTest {

    private DynamicPrice testPrice;

    @BeforeEach
    void setUp() {
        testPrice = new DynamicPrice(1L);
    }

    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        DynamicPrice price = new DynamicPrice();
        assertNull(price.getId());
        assertEquals(0, price.getDayOfWeek());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        DynamicPrice price = new DynamicPrice(5L);
        assertEquals(5L, price.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Parking parking = new Parking(10L);
        
        DynamicPrice price = new DynamicPrice(1L, 1, "08:00", "18:00", 1.0, 5.0, now, parking);
        
        assertEquals(1L, price.getId());
        assertEquals(1, price.getDayOfWeek());
        assertEquals("08:00", price.getStartHour());
        assertEquals("18:00", price.getEndHour());
        assertEquals(1.0, price.getMinPrice());
        assertEquals(5.0, price.getMaxPrice());
        assertEquals(now, price.getCreateAt());
        assertEquals(parking, price.getParking());
    }


    @Test
    @DisplayName("Getter/Setter - DayOfWeek: Should preserve integer value")
    void shouldSetAndGetDayOfWeek() {
        testPrice.setDayOfWeek(3);
        assertEquals(3, testPrice.getDayOfWeek());
    }

    @Test
    @DisplayName("Getter/Setter - StartHour: Should preserve string value")
    void shouldSetAndGetStartHour() {
        testPrice.setStartHour("09:00");
        assertEquals("09:00", testPrice.getStartHour());
    }

    @Test
    @DisplayName("Getter/Setter - EndHour: Should preserve string value")
    void shouldSetAndGetEndHour() {
        testPrice.setEndHour("21:00");
        assertEquals("21:00", testPrice.getEndHour());
    }

    @Test
    @DisplayName("Getter/Setter - MinPrice: Should preserve double value")
    void shouldSetAndGetMinPrice() {
        testPrice.setMinPrice(2.5);
        assertEquals(2.5, testPrice.getMinPrice());
    }

    @Test
    @DisplayName("Getter/Setter - MaxPrice: Should preserve double value")
    void shouldSetAndGetMaxPrice() {
        testPrice.setMaxPrice(7.5);
        assertEquals(7.5, testPrice.getMaxPrice());
    }

    @Test
    @DisplayName("Getter/Setter - CreateAt: Should preserve timestamp")
    void shouldSetAndGetCreateAt() {
        LocalDateTime now = LocalDateTime.now();
        testPrice.setCreateAt(now);
        assertEquals(now, testPrice.getCreateAt());
    }

    @Test
    @DisplayName("Getter/Setter - Parking: Should preserve parking relationship")
    void shouldSetAndGetParking() {
        Parking parking = new Parking(1L);
        testPrice.setParking(parking);
        assertEquals(parking, testPrice.getParking());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testPrice, testPrice);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testPrice, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testPrice, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        DynamicPrice other = new DynamicPrice(1L);
        assertEquals(testPrice, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        DynamicPrice other = new DynamicPrice(2L);
        assertNotEquals(testPrice, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        DynamicPrice d1 = new DynamicPrice();
        DynamicPrice d2 = new DynamicPrice();
        assertEquals(d1, d2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        DynamicPrice d1 = new DynamicPrice(1L);
        DynamicPrice d2 = new DynamicPrice();
        assertNotEquals(d1, d2);
        assertNotEquals(d2, d1);
    }

    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        DynamicPrice other = new DynamicPrice(1L);
        assertEquals(testPrice.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        DynamicPrice other = new DynamicPrice(2L);
        assertNotEquals(testPrice.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        DynamicPrice d1 = new DynamicPrice();
        DynamicPrice d2 = new DynamicPrice();
        assertEquals(d1.hashCode(), d2.hashCode());
    }
}
