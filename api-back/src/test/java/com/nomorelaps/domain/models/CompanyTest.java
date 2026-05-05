package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CompanyTest {

    @Test
    @DisplayName("Company - Constructors and Getters/Setters should work")
    void testCompany() {
        Company c1 = new Company();
        assertNotNull(c1.getParkings());

        Company c2 = new Company(1L);
        assertEquals(1L, c2.getId());

        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L);
        Set<Parking> parkings = new HashSet<>();
        Company c3 = new Company(1L, "Corp", "key", "pass", "+34", "corp@test.com", "B123", now, user, parkings);
        
        assertEquals("Corp", c3.getName());
        assertEquals("key", c3.getApiKey());
        assertEquals(user, c3.getUser());
        assertEquals(parkings, c3.getParkings());

        c1.setId(2L);
        c1.setName("New Corp");
        c1.setApiKey("newKey");
        c1.setPassword("newPass");
        c1.setPhone("+349");
        c1.setEmail("new@test.com");
        c1.setCif("A888");
        c1.setRegisterDay(now);
        c1.setUser(user);
        c1.setParkings(parkings);

        assertEquals(2L, c1.getId());
        assertEquals("New Corp", c1.getName());
    }

    @Test
    @DisplayName("Company - Equals and HashCode")
    void testCompanyEquals() {
        Company c1 = new Company(1L);
        Company c2 = new Company(1L);
        Company c3 = new Company(2L);

        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
        assertNotEquals(c1, null);
        assertNotEquals(c1, new Object());
        assertEquals(c1, c1);
    }
}
