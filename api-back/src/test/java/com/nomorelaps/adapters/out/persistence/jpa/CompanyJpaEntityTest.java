package com.nomorelaps.adapters.out.persistence.jpa;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/**
 * Unit tests for CompanyJpaEntity.
 * Verifies data integrity, all constructors, and all branches of equals/hashCode.
 */
class CompanyJpaEntityTest {

    private CompanyJpaEntity testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new CompanyJpaEntity();
    }

    @Test
    @DisplayName("Constructor - Empty should initialize object")
    void shouldInitializeEmpty() {
        assertNotNull(new CompanyJpaEntity());
    }

    @Test
    @DisplayName("Constructor - ID constructor should correctly set ID")
    void shouldInitializeWithId() {
        CompanyJpaEntity entity = new CompanyJpaEntity(10L);
        assertEquals(10L, entity.getId());
    }

    @Test
    @DisplayName("Constructor - Full constructor should correctly set all fields")
    void shouldInitializeWithAllFields() {
        LocalDateTime now = LocalDateTime.now();
        UserJpaEntity user = new UserJpaEntity(55L);
        Set<ParkingJpaEntity> parkings = new HashSet<>();

        CompanyJpaEntity entity = new CompanyJpaEntity(3L, "Corp", "pass", "key", "123", 
            "email@corp.com", "CIF123", now, user, parkings);

        assertEquals(3L, entity.getId());
        assertEquals("Corp", entity.getName());
        assertEquals("pass", entity.getPassword());
        assertEquals("key", entity.getApiKey());
        assertEquals("123", entity.getPhone());
        assertEquals("email@corp.com", entity.getEmail());
        assertEquals("CIF123", entity.getCif());
        assertEquals(now, entity.getRegisterDay());
        assertEquals(user, entity.getUser());
        assertEquals(parkings, entity.getParkings());
    }

    @Test
    @DisplayName("Setters - Should update basic fields")
    void shouldSetBasicFields() {
        testCompany.setName("Corp Inc");
        assertEquals("Corp Inc", testCompany.getName());
        
        testCompany.setEmail("corp@test.com");
        assertEquals("corp@test.com", testCompany.getEmail());
        
        testCompany.setApiKey("key-123");
        assertEquals("key-123", testCompany.getApiKey());
        
        testCompany.setPassword("secret");
        assertEquals("secret", testCompany.getPassword());
        
        testCompany.setPhone("999888");
        assertEquals("999888", testCompany.getPhone());
        
        testCompany.setCif("CIF-B");
        assertEquals("CIF-B", testCompany.getCif());
    }

    @Test
    @DisplayName("Relationships - Should update User and Parkings")
    void shouldSetRelationships() {
        UserJpaEntity user = new UserJpaEntity(55L);
        Set<ParkingJpaEntity> parkings = new HashSet<>();

        testCompany.setUser(user);
        testCompany.setParkings(parkings);

        assertEquals(user, testCompany.getUser());
        assertEquals(parkings, testCompany.getParkings());
    }

    @Test
    @DisplayName("onCreate - Should set registration day if null")
    void shouldSetTimestampOnCreate() {
        assertNull(testCompany.getRegisterDay());
        testCompany.onCreate();
        assertNotNull(testCompany.getRegisterDay());
    }

    @Test
    @DisplayName("Equals - Should handle same object and same ID")
    void shouldVerifyEquality() {
        CompanyJpaEntity c1 = new CompanyJpaEntity(1L);
        CompanyJpaEntity c2 = new CompanyJpaEntity(1L);
        CompanyJpaEntity c3 = new CompanyJpaEntity(2L);

        assertEquals(c1, c1);
        assertEquals(c1, c2);
        assertEquals(c1.hashCode(), c2.hashCode());
        assertNotEquals(c1, c3);
        assertNotEquals(c1, null);
        assertNotEquals(c1, "not a company");
    }

    @Test
    @DisplayName("Equals - Should handle null IDs")
    void equalsNullIds() {
        CompanyJpaEntity c1 = new CompanyJpaEntity(null);
        CompanyJpaEntity c2 = new CompanyJpaEntity(null);
        assertEquals(c1, c2);
        
        CompanyJpaEntity c3 = new CompanyJpaEntity(1L);
        assertNotEquals(c1, c3);
    }
}
