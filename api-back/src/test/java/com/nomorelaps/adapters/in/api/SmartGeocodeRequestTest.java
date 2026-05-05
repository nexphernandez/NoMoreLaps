package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SmartGeocodeRequestTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SmartGeocodeRequest")
    void testGettersAndSetters() {
        SmartGeocodeRequest dto = new SmartGeocodeRequest();
        assertNotNull(dto);

        dto.setQuery("dummy1");
        assertEquals("dummy1", dto.getQuery());
    }

    @Test
    @DisplayName("Should test parameterized constructor")
    void testParameterizedConstructor() {
        SmartGeocodeRequest dto = new SmartGeocodeRequest("Madrid");
        assertEquals("Madrid", dto.getQuery());
    }


    @Test
    @DisplayName("Should test equals and hashCode branches for SmartGeocodeRequest")
    void testEqualsAndHashCode() {
        SmartGeocodeRequest dto1 = new SmartGeocodeRequest();
        SmartGeocodeRequest dto2 = new SmartGeocodeRequest();
        SmartGeocodeRequest dto3 = new SmartGeocodeRequest();

        dto1.setQuery("dummy1");
        dto2.setQuery("dummy1");
        dto3.setQuery("dummy2");

        // Base checks
        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Branch coverage for each field
        SmartGeocodeRequest tempquery = new SmartGeocodeRequest();
        tempquery.setQuery("dummy1");
        tempquery.setQuery(null);
        dto1.equals(tempquery);
        tempquery.equals(dto1);
        tempquery.setQuery("dummy2");
        dto1.equals(tempquery);

    }
}
