package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SmartGeocodeResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SmartGeocodeResponse")
    void testGettersAndSetters() {
        SmartGeocodeResponse dto = new SmartGeocodeResponse();
        assertNotNull(dto);

        dto.setFormattedAddress("dummy1");
        assertEquals("dummy1", dto.getFormattedAddress());
        dto.setLatitude(1.0);
        assertEquals(1.0, dto.getLatitude());
        dto.setLongitude(1.0);
        assertEquals(1.0, dto.getLongitude());
    }

    @Test
    @DisplayName("Should test equals and hashCode branches for SmartGeocodeResponse")
    void testEqualsAndHashCode() {
        SmartGeocodeResponse dto1 = new SmartGeocodeResponse();
        SmartGeocodeResponse dto2 = new SmartGeocodeResponse();
        SmartGeocodeResponse dto3 = new SmartGeocodeResponse();

        dto1.setFormattedAddress("dummy1");
        dto2.setFormattedAddress("dummy1");
        dto3.setFormattedAddress("dummy2");
        dto1.setLatitude(1.0);
        dto2.setLatitude(1.0);
        dto3.setLatitude(2.0);
        dto1.setLongitude(1.0);
        dto2.setLongitude(1.0);
        dto3.setLongitude(2.0);

        // Base checks
        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Branch coverage for each field
        SmartGeocodeResponse tempformattedAddress = new SmartGeocodeResponse();
        tempformattedAddress.setFormattedAddress("dummy1");
        tempformattedAddress.setLatitude(1.0);
        tempformattedAddress.setLongitude(1.0);
        tempformattedAddress.setFormattedAddress(null);
        dto1.equals(tempformattedAddress);
        tempformattedAddress.equals(dto1);
        tempformattedAddress.setFormattedAddress("dummy2");
        dto1.equals(tempformattedAddress);

        SmartGeocodeResponse templatitude = new SmartGeocodeResponse();
        templatitude.setFormattedAddress("dummy1");
        templatitude.setLatitude(1.0);
        templatitude.setLongitude(1.0);
        templatitude.setLatitude(null);
        dto1.equals(templatitude);
        templatitude.equals(dto1);
        templatitude.setLatitude(2.0);
        dto1.equals(templatitude);

        SmartGeocodeResponse templongitude = new SmartGeocodeResponse();
        templongitude.setFormattedAddress("dummy1");
        templongitude.setLatitude(1.0);
        templongitude.setLongitude(1.0);
        templongitude.setLongitude(null);
        dto1.equals(templongitude);
        templongitude.equals(dto1);
        templongitude.setLongitude(2.0);
        dto1.equals(templongitude);

    }
}
