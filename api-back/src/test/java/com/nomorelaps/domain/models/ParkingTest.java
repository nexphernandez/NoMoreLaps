package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ParkingTest {

    @Test
    @DisplayName("Parking - Constructors and Getters/Setters should work")
    void testParkingConstructorsAndAccessors() {
        Parking p1 = new Parking();
        assertNotNull(p1.getParkingSpots());
        assertNotNull(p1.getDynamicPrice());
        assertEquals(2.0, p1.getPricePerHour()); // default value

        Parking p2 = new Parking(1L);
        assertEquals(1L, p2.getId());

        LocalDateTime now = LocalDateTime.now();
        Company company = new Company(1L);
        Set<ParkingSpot> spots = new HashSet<>();
        Set<DynamicPrice> prices = new HashSet<>();
        Parking p3 = new Parking(1L, "Street 1", "Park 1", 40.0, -3.0, now, now.plusHours(8), now, 3.0, company, 10.0, 30, spots, prices);
        
        assertEquals(1L, p3.getId());
        assertEquals("Street 1", p3.getAddress());
        assertEquals("Park 1", p3.getName());
        assertEquals(40.0, p3.getLatitude());
        assertEquals(-3.0, p3.getLongitude());
        assertEquals(now, p3.getOpeningTime());
        assertEquals(3.0, p3.getPricePerHour());
        assertEquals(company, p3.getCompany());
        assertEquals(10.0, p3.getSanctionAmount());
        assertEquals(30, p3.getSanctionIntervalInMinutes());
        assertEquals(spots, p3.getParkingSpots());

        // Setters
        p1.setId(2L);
        p1.setAddress("Street 2");
        p1.setName("Park 2");
        p1.setLatitude(41.0);
        p1.setLongitude(-4.0);
        p1.setOpeningTime(now);
        p1.setClosingTime(now.plusHours(10));
        p1.setCreatedAt(now);
        p1.setPricePerHour(4.0);
        p1.setCompany(company);
        p1.setSanctionAmount(5.0);
        p1.setSanctionIntervalInMinutes(20);
        p1.setParkingSpots(spots);

        assertEquals(2L, p1.getId());
        assertEquals("Park 2", p1.getName());
        assertEquals(4.0, p1.getPricePerHour());
        assertEquals(company, p1.getCompany());
    }

    @Test
    @DisplayName("Parking - Equals and HashCode")
    void testParkingEquals() {
        Parking p1 = new Parking(1L);
        Parking p2 = new Parking(1L);
        Parking p3 = new Parking(2L);

        assertEquals(p1, p2);
        assertEquals(p1.hashCode(), p2.hashCode());
        assertNotEquals(p1, p3);
        assertNotEquals(p1, null);
        assertNotEquals(p1, new Object());
        assertEquals(p1, p1);
    }
}
