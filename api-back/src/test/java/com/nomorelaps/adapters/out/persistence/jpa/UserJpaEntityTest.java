package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for UserJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class UserJpaEntityTest {

    private UserJpaEntity testUser;

    @BeforeEach
    void setUp() {
        testUser = new UserJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new UserJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        UserJpaEntity entity = new UserJpaEntity(10L);
        assertEquals(10L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        RoleJpaEntity role = new RoleJpaEntity(1L);
        Set<CompanyJpaEntity> companies = new HashSet<>();
        Set<ReservationJpaEntity> reservations = new HashSet<>();
        Set<SanctionJpaEntity> sanctions = new HashSet<>();

        UserJpaEntity entity = new UserJpaEntity(3L, "Name", "email", "pass", true, "avatar", "123", 
            now, role, companies, reservations, sanctions);

        assertEquals(3L, entity.getId());
        assertEquals("Name", entity.getName());
        assertEquals("email", entity.getEmail());
        assertEquals("pass", entity.getPassword());
        assertTrue(entity.getCalendarEnable());
        assertEquals("avatar", entity.getAvatar());
        assertEquals("123", entity.getPhone());
        assertEquals(now, entity.getCreateAt());
        assertEquals(role, entity.getRole());
        assertEquals(companies, entity.getCompanies());
        assertEquals(reservations, entity.getReservations());
        assertEquals(sanctions, entity.getSanctions());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testUser.setName("Alice");
        assertEquals("Alice", testUser.getName());
        
        testUser.setEmail("alice@test.com");
        assertEquals("alice@test.com", testUser.getEmail());
        
        testUser.setPassword("secret");
        assertEquals("secret", testUser.getPassword());
        
        testUser.setCalendarEnable(true);
        assertTrue(testUser.getCalendarEnable());
        
        testUser.setAvatar("alice.png");
        assertEquals("alice.png", testUser.getAvatar());
        
        testUser.setPhone("123456");
        assertEquals("123456", testUser.getPhone());
    }

    @Test
    @DisplayName("Relationships - Should update Role and collections")
    void shouldSetRelationships() {
        RoleJpaEntity role = new RoleJpaEntity();
        Set<CompanyJpaEntity> companies = new HashSet<>();
        Set<ReservationJpaEntity> reservations = new HashSet<>();
        Set<SanctionJpaEntity> sanctions = new HashSet<>();

        testUser.setRole(role);
        testUser.setCompanies(companies);
        testUser.setReservations(reservations);
        testUser.setSanctions(sanctions);

        assertEquals(role, testUser.getRole());
        assertEquals(companies, testUser.getCompanies());
        assertEquals(reservations, testUser.getReservations());
        assertEquals(sanctions, testUser.getSanctions());
    }

    @Test
    @DisplayName("onCreate - Should set creation timestamp if null")
    void shouldSetTimestampOnCreate() {
        assertNull(testUser.getCreateAt());
        testUser.onCreate();
        assertNotNull(testUser.getCreateAt());
    }

    @Test
    @DisplayName("Equals - Should handle same object")
    void equalsSameObject() {
        assertEquals(testUser, testUser);
    }

    @Test
    @DisplayName("Equals - Should handle null and different classes")
    void equalsDifferentTypes() {
        assertNotEquals(testUser, null);
        assertNotEquals(testUser, "not a user");
    }

    @Test
    @DisplayName("Equals - Should handle same ID")
    void equalsSameId() {
        UserJpaEntity u1 = new UserJpaEntity(1L);
        UserJpaEntity u2 = new UserJpaEntity(1L);
        assertEquals(u1, u2);
        assertEquals(u1.hashCode(), u2.hashCode());
    }

    @Test
    @DisplayName("Equals - Should handle different IDs")
    void equalsDifferentIds() {
        UserJpaEntity u1 = new UserJpaEntity(1L);
        UserJpaEntity u2 = new UserJpaEntity(2L);
        assertNotEquals(u1, u2);
    }

    @Test
    @DisplayName("Equals - Should handle null IDs (both null)")
    void equalsNullIds() {
        UserJpaEntity u1 = new UserJpaEntity(null);
        UserJpaEntity u2 = new UserJpaEntity(null);
        assertEquals(u1, u2);
    }

    @Test
    @DisplayName("Equals - Should handle one null ID")
    void equalsOneNullId() {
        UserJpaEntity u1 = new UserJpaEntity(1L);
        UserJpaEntity u2 = new UserJpaEntity(null);
        assertNotEquals(u1, u2);
        assertNotEquals(u2, u1);
    }
}
