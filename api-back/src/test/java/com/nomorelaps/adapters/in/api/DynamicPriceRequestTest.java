package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DynamicPriceRequestTest {

    @Test
    @DisplayName("DynamicPriceRequest - Constructors and Accessors")
    void testRequest() {
        DynamicPriceRequest req1 = new DynamicPriceRequest();
        req1.setDayOfWeek(1);
        req1.setStartHour("08:00");
        req1.setEndHour("20:00");
        req1.setMinPrice(1.0);
        req1.setMaxPrice(5.0);

        assertEquals(1, req1.getDayOfWeek());
        assertEquals("08:00", req1.getStartHour());
        assertEquals("20:00", req1.getEndHour());
        assertEquals(1.0, req1.getMinPrice());
        assertEquals(5.0, req1.getMaxPrice());

        DynamicPriceRequest req2 = new DynamicPriceRequest(2, "10:00", "22:00", 2.0, 10.0);
        assertEquals(2, req2.getDayOfWeek());
        assertEquals(10.0, req2.getMaxPrice());
    }
}
