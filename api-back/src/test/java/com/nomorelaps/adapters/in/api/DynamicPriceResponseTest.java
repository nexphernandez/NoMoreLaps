package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DynamicPriceResponse DTO.
 * Verifies that dynamic pricing data is correctly mapped and logical equality is maintained.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class DynamicPriceResponseTest {

    private DynamicPriceResponse dynamicPriceResponse;
    private LocalDateTime now;

    @BeforeEach
    void setUp() {
        dynamicPriceResponse = new DynamicPriceResponse();
        now = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        DynamicPriceResponse idResponse = new DynamicPriceResponse(50L);
        assertEquals(50L, idResponse.getId());
    }

    @Test
    @DisplayName("Full Constructor - Should correctly map all fields")
    void shouldInitializeWithFullConstructor() {
        DynamicPriceResponse fullResponse = new DynamicPriceResponse(1L, 1, "08:00", "18:00", 2.0, 4.0, now);

        assertEquals(1L, fullResponse.getId());
        assertEquals(1, fullResponse.getDayOfWeek());
        assertEquals("08:00", fullResponse.getStartHour());
        assertEquals("18:00", fullResponse.getEndHour());
        assertEquals(2.0, fullResponse.getMinPrice());
        assertEquals(4.0, fullResponse.getMaxPrice());
        assertEquals(now, fullResponse.getCreateAt());
    }

    @Test
    @DisplayName("id - Should set and get the ID")
    void shouldSetAndGetId() {
        dynamicPriceResponse.setId(10L);
        assertEquals(10L, dynamicPriceResponse.getId());
    }

    @Test
    @DisplayName("dayOfWeek - Should set and get the day")
    void shouldSetAndGetDayOfWeek() {
        dynamicPriceResponse.setDayOfWeek(3);
        assertEquals(3, dynamicPriceResponse.getDayOfWeek());
    }

    @Test
    @DisplayName("startHour - Should set and get start hour")
    void shouldSetAndGetStartHour() {
        dynamicPriceResponse.setStartHour("10:00");
        assertEquals("10:00", dynamicPriceResponse.getStartHour());
    }

    @Test
    @DisplayName("endHour - Should set and get end hour")
    void shouldSetAndGetEndHour() {
        dynamicPriceResponse.setEndHour("22:00");
        assertEquals("22:00", dynamicPriceResponse.getEndHour());
    }

    @Test
    @DisplayName("minPrice - Should set and get minimum price")
    void shouldSetAndGetMinPrice() {
        dynamicPriceResponse.setMinPrice(1.50);
        assertEquals(1.50, dynamicPriceResponse.getMinPrice());
    }

    @Test
    @DisplayName("maxPrice - Should set and get maximum price")
    void shouldSetAndGetMaxPrice() {
        dynamicPriceResponse.setMaxPrice(5.50);
        assertEquals(5.50, dynamicPriceResponse.getMaxPrice());
    }

    @Test
    @DisplayName("createAt - Should set and get creation timestamp")
    void shouldSetAndGetCreateAt() {
        dynamicPriceResponse.setCreateAt(now);
        assertEquals(now, dynamicPriceResponse.getCreateAt());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        DynamicPriceResponse first = new DynamicPriceResponse(1L);
        DynamicPriceResponse second = new DynamicPriceResponse(1L);
        DynamicPriceResponse third = new DynamicPriceResponse(2L);

        assertEquals(first, first, "Should be equal to itself");
        assertEquals(first, second, "Should be equal if IDs match");
        assertNotEquals(first, third, "Should not be equal if IDs differ");
        assertNotEquals(first, null, "Should not be equal to null");
        assertNotEquals(first, "different type", "Should return false for different class (instanceof test)");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same ID")
    void shouldHaveConsistentHashCode() {
        DynamicPriceResponse first = new DynamicPriceResponse(1L);
        DynamicPriceResponse second = new DynamicPriceResponse(1L);

        assertEquals(first.hashCode(), second.hashCode());
    }
}
