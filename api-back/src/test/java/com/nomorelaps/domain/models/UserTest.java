package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for User domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class UserTest {

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        User user = new User();
        assertNull(user.getId());
        assertNotNull(user.getCompanies());
        assertNotNull(user.getReservations());
        assertNotNull(user.getSanctions());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        User user = new User(5L);
        assertEquals(5L, user.getId());
    }

    @Test
    @DisplayName("Constructor - Profile: Should initialize basic profile correctly")
    void shouldInitializeProfileCorrectlyUsingProfileConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User user = new User(1L, "Alice", "alice@test.com", "pass", true, "avatar", "123", now);
        
        assertEquals(1L, user.getId());
        assertEquals("Alice", user.getName());
        assertEquals("alice@test.com", user.getEmail());
        assertEquals("pass", user.getPassword());
        assertTrue(user.isCalendarEnable());
        assertEquals("avatar", user.getAvatar());
        assertEquals("123", user.getPhone());
        assertEquals(now, user.getCreateAt());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        Role role = new Role(1L);
        Set<Company> companies = new HashSet<>();
        Set<Reservation> reservations = new HashSet<>();
        Set<Sanction> sanctions = new HashSet<>();
        
        User user = new User(1L, "Alice", "alice@test.com", "pass", true, "avatar", "123", now, role, companies, reservations, sanctions);
        
        assertEquals(1L, user.getId());
        assertEquals(role, user.getRole());
        assertEquals(companies, user.getCompanies());
        assertEquals(reservations, user.getReservations());
        assertEquals(sanctions, user.getSanctions());
    }


    @Test
    @DisplayName("Getter/Setter - Name: Should preserve string identity")
    void shouldSetAndGetName() {
        testUser.setName("Bob");
        assertEquals("Bob", testUser.getName());
    }

    @Test
    @DisplayName("Getter/Setter - Email: Should preserve mail address")
    void shouldSetAndGetEmail() {
        testUser.setEmail("bob@test.com");
        assertEquals("bob@test.com", testUser.getEmail());
    }

    @Test
    @DisplayName("Getter/Setter - Password: Should preserve credential hash")
    void shouldSetAndGetPassword() {
        testUser.setPassword("secret");
        assertEquals("secret", testUser.getPassword());
    }

    @Test
    @DisplayName("Getter/Setter - CalendarEnable: Should preserve boolean flag")
    void shouldSetAndGetCalendarEnable() {
        testUser.setCalendarEnable(true);
        assertTrue(testUser.isCalendarEnable());
    }

    @Test
    @DisplayName("Getter/Setter - Avatar: Should preserve profile image URL")
    void shouldSetAndGetAvatar() {
        testUser.setAvatar("profile.jpg");
        assertEquals("profile.jpg", testUser.getAvatar());
    }

    @Test
    @DisplayName("Getter/Setter - Phone: Should preserve contact number")
    void shouldSetAndGetPhone() {
        testUser.setPhone("555123");
        assertEquals("555123", testUser.getPhone());
    }

    @Test
    @DisplayName("Getter/Setter - CreateAt: Should preserve timestamp")
    void shouldSetAndGetCreateAt() {
        LocalDateTime now = LocalDateTime.now();
        testUser.setCreateAt(now);
        assertEquals(now, testUser.getCreateAt());
    }

    @Test
    @DisplayName("Getter/Setter - Role: Should preserve relationship")
    void shouldSetAndGetRole() {
        Role role = new Role(1L);
        testUser.setRole(role);
        assertEquals(role, testUser.getRole());
    }

    @Test
    @DisplayName("Getter/Setter - Collections: Should preserve related entities")
    void shouldSetAndGetCollections() {
        Set<Company> companies = new HashSet<>();
        Set<Reservation> reservations = new HashSet<>();
        Set<Sanction> sanctions = new HashSet<>();
        testUser.setCompanies(companies);
        testUser.setReservations(reservations);
        testUser.setSanctions(sanctions);
        assertEquals(companies, testUser.getCompanies());
        assertEquals(reservations, testUser.getReservations());
        assertEquals(sanctions, testUser.getSanctions());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testUser, testUser);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testUser, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testUser, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        User other = new User(1L);
        assertEquals(testUser, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        User other = new User(2L);
        assertNotEquals(testUser, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        User u1 = new User();
        User u2 = new User();
        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        User u1 = new User(1L);
        User u2 = new User();
        assertNotEquals(u1, u2);
        assertNotEquals(u2, u1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        User other = new User(1L);
        assertEquals(testUser.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        User other = new User(2L);
        assertNotEquals(testUser.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        User u1 = new User();
        User u2 = new User();
        assertEquals(u1.hashCode(), u2.hashCode());
    }
}
