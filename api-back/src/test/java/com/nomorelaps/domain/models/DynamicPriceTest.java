package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DynamicPriceTest {

    @Test
    @DisplayName("DynamicPrice - Constructors and Getters/Setters should work")
    void testDynamicPrice() {
        DynamicPrice d1 = new DynamicPrice();
        DynamicPrice d2 = new DynamicPrice(1L);
        assertEquals(1L, d2.getId());

        LocalDateTime now = LocalDateTime.now();
        Parking parking = new Parking(1L);
        DynamicPrice d3 = new DynamicPrice(1L, 1, "08:00", "20:00", 1.0, 5.0, now, parking);

        assertEquals(1, d3.getDayOfWeek());
        assertEquals("08:00", d3.getStartHour());
        assertEquals("20:00", d3.getEndHour());
        assertEquals(1.0, d3.getMinPrice());
        assertEquals(5.0, d3.getMaxPrice());
        assertEquals(parking, d3.getParking());

        d1.setId(2L);
        d1.setDayOfWeek(2);
        d1.setStartHour("10:00");
        d1.setEndHour("22:00");
        d1.setMinPrice(2.0);
        d1.setMaxPrice(10.0);
        d1.setParking(parking);

        assertEquals(2L, d1.getId());
        assertEquals(2, d1.getDayOfWeek());
        assertEquals(2.0, d1.getMinPrice());
    }

    @Test
    @DisplayName("DynamicPrice - Equals and HashCode")
    void testDynamicPriceEquals() {
        DynamicPrice d1 = new DynamicPrice(1L);
        DynamicPrice d2 = new DynamicPrice(1L);
        DynamicPrice d3 = new DynamicPrice(2L);

        assertEquals(d1, d2);
        assertEquals(d1.hashCode(), d2.hashCode());
        assertNotEquals(d1, d3);
        assertNotEquals(d1, null);
        assertNotEquals(d1, new Object());
        assertEquals(d1, d1);
    }
}
