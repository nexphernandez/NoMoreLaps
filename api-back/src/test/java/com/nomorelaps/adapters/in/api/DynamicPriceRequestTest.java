package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for DynamicPriceRequest DTO.
 * Verifies that dynamic pricing configuration is correctly handled.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class DynamicPriceRequestTest {

    private DynamicPriceRequest pricingRequest;

    @BeforeEach
    void setUp() {
        pricingRequest = new DynamicPriceRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithFullConstructor() {
        DynamicPriceRequest fullRequest = new DynamicPriceRequest(1, "09:00", "18:00", 1.5, 3.5);

        assertEquals(1, fullRequest.getDayOfWeek());
        assertEquals("09:00", fullRequest.getStartHour());
        assertEquals("18:00", fullRequest.getEndHour());
        assertEquals(1.5, fullRequest.getMinPrice());
        assertEquals(3.5, fullRequest.getMaxPrice());
    }

    @Test
    @DisplayName("dayOfWeek - Should set and get the day of week index")
    void shouldSetAndGetDayOfWeek() {
        pricingRequest.setDayOfWeek(5); // Friday
        assertEquals(5, pricingRequest.getDayOfWeek());
    }

    @Test
    @DisplayName("startHour - Should set and get the starting hour string")
    void shouldSetAndGetStartHour() {
        pricingRequest.setStartHour("07:30");
        assertEquals("07:30", pricingRequest.getStartHour());
    }

    @Test
    @DisplayName("endHour - Should set and get the ending hour string")
    void shouldSetAndGetEndHour() {
        pricingRequest.setEndHour("23:45");
        assertEquals("23:45", pricingRequest.getEndHour());
    }

    @Test
    @DisplayName("minPrice - Should set and get the minimum price threshold")
    void shouldSetAndGetMinPrice() {
        pricingRequest.setMinPrice(0.50);
        assertEquals(0.50, pricingRequest.getMinPrice());
    }

    @Test
    @DisplayName("maxPrice - Should set and get the maximum price threshold")
    void shouldSetAndGetMaxPrice() {
        pricingRequest.setMaxPrice(9.99);
        assertEquals(9.99, pricingRequest.getMaxPrice());
    }
}
