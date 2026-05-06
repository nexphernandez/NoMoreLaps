package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class UserTest {

    @Test
    @DisplayName("User - Constructors and Getters/Setters should work")
    void testUserConstructorsAndAccessors() {
        User user1 = new User();
        assertNull(user1.getId());

        User user2 = new User(1L);
        assertEquals(1L, user2.getId());

        LocalDateTime now = LocalDateTime.now();
        User user3 = new User(1L, "Alice", "alice@test.com", "pass", true, "avatar.png", "+34600", now);
        assertEquals(1L, user3.getId());
        assertEquals("Alice", user3.getName());
        assertEquals("alice@test.com", user3.getEmail());
        assertEquals("pass", user3.getPassword());
        assertTrue(user3.isCalendarEnable());
        assertEquals("avatar.png", user3.getAvatar());
        assertEquals("+34600", user3.getPhone());
        assertEquals(now, user3.getCreateAt());

        Role role = new Role(1L);
        Set<Company> companies = new HashSet<>();
        Set<Reservation> reservations = new HashSet<>();
        Set<Sanction> sanctions = new HashSet<>();
        User user4 = new User(1L, "Alice", "alice@test.com", "pass", true, "avatar.png", "+34600", now, role, companies, reservations, sanctions);
        assertEquals(role, user4.getRole());
        assertEquals(companies, user4.getCompanies());
        assertEquals(reservations, user4.getReservations());
        assertEquals(sanctions, user4.getSanctions());

        user1.setId(2L);
        user1.setName("Bob");
        user1.setEmail("bob@test.com");
        user1.setPassword("newpass");
        user1.setCalendarEnable(false);
        user1.setAvatar("bob.png");
        user1.setPhone("+34700");
        user1.setCreateAt(now);
        user1.setRole(role);
        user1.setCompanies(companies);
        user1.setReservations(reservations);
        user1.setSanctions(sanctions);

        assertEquals(2L, user1.getId());
        assertEquals("Bob", user1.getName());
        assertEquals("bob@test.com", user1.getEmail());
        assertEquals("newpass", user1.getPassword());
        assertFalse(user1.isCalendarEnable());
        assertEquals("bob.png", user1.getAvatar());
        assertEquals("+34700", user1.getPhone());
        assertEquals(now, user1.getCreateAt());
        assertEquals(role, user1.getRole());
    }

    @Test
    @DisplayName("User - Equals and HashCode")
    void testUserEquals() {
        User u1 = new User(1L);
        User u2 = new User(1L);
        User u3 = new User(2L);
        User uNull = null;
        Object other = new Object();

        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
        assertNotEquals(u1, u3);
        assertNotEquals(u1, uNull);
        assertNotEquals(u1, other);
        assertEquals(u1, u1);
    }
}
