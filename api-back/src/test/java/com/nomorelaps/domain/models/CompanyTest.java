package com.nomorelaps.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for Company domain model.
 * Verifies data integrity and all branches of equals/hashCode with extreme granularity
 * as per the New Backend Test Refactoring Plan.
 */
class CompanyTest {

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company(1L);
    }


    @Test
    @DisplayName("Constructor - Empty: Should initialize with default values")
    void shouldInitializeWithDefaultValuesUsingEmptyConstructor() {
        Company company = new Company();
        assertNull(company.getId());
        assertNotNull(company.getParkings());
        assertTrue(company.getParkings().isEmpty());
    }

    @Test
    @DisplayName("Constructor - ID: Should initialize with specified identifier")
    void shouldInitializeWithIdUsingIdConstructor() {
        Company company = new Company(5L);
        assertEquals(5L, company.getId());
    }

    @Test
    @DisplayName("Constructor - Full: Should initialize all fields correctly")
    void shouldInitializeAllFieldsUsingFullConstructor() {
        LocalDateTime now = LocalDateTime.now();
        User admin = new User(10L);
        Set<Parking> parkings = new HashSet<>();
        
        Company company = new Company(1L, "Name", "Key", "Pass", "123", "a@b.com", "CIF", now, admin, parkings);
        
        assertEquals(1L, company.getId());
        assertEquals("Name", company.getName());
        assertEquals("Key", company.getApiKey());
        assertEquals("Pass", company.getPassword());
        assertEquals("123", company.getPhone());
        assertEquals("a@b.com", company.getEmail());
        assertEquals("CIF", company.getCif());
        assertEquals(now, company.getRegisterDay());
        assertEquals(admin, company.getUser());
        assertEquals(parkings, company.getParkings());
    }


    @Test
    @DisplayName("Getter/Setter - Name: Should preserve string value")
    void shouldSetAndGetName() {
        testCompany.setName("Test Corp");
        assertEquals("Test Corp", testCompany.getName());
    }

    @Test
    @DisplayName("Getter/Setter - ApiKey: Should preserve token value")
    void shouldSetAndGetApiKey() {
        testCompany.setApiKey("nml_key");
        assertEquals("nml_key", testCompany.getApiKey());
    }

    @Test
    @DisplayName("Getter/Setter - Password: Should preserve credential string")
    void shouldSetAndGetPassword() {
        testCompany.setPassword("secret");
        assertEquals("secret", testCompany.getPassword());
    }

    @Test
    @DisplayName("Getter/Setter - Phone: Should preserve contact number")
    void shouldSetAndGetPhone() {
        testCompany.setPhone("123456");
        assertEquals("123456", testCompany.getPhone());
    }

    @Test
    @DisplayName("Getter/Setter - Email: Should preserve mail address")
    void shouldSetAndGetEmail() {
        testCompany.setEmail("test@corp.com");
        assertEquals("test@corp.com", testCompany.getEmail());
    }

    @Test
    @DisplayName("Getter/Setter - CIF: Should preserve legal identifier")
    void shouldSetAndGetCif() {
        testCompany.setCif("B123");
        assertEquals("B123", testCompany.getCif());
    }

    @Test
    @DisplayName("Getter/Setter - RegisterDay: Should preserve timestamp")
    void shouldSetAndGetRegisterDay() {
        LocalDateTime now = LocalDateTime.now();
        testCompany.setRegisterDay(now);
        assertEquals(now, testCompany.getRegisterDay());
    }

    @Test
    @DisplayName("Getter/Setter - User: Should preserve user relationship")
    void shouldSetAndGetUser() {
        User user = new User(1L);
        testCompany.setUser(user);
        assertEquals(user, testCompany.getUser());
    }

    @Test
    @DisplayName("Getter/Setter - Parkings: Should preserve set of parkings")
    void shouldSetAndGetParkings() {
        Set<Parking> parkings = new HashSet<>();
        testCompany.setParkings(parkings);
        assertEquals(parkings, testCompany.getParkings());
    }


    @Test
    @DisplayName("equals - Same instance: Should return true")
    void equals_ShouldReturnTrueForSameInstance() {
        assertEquals(testCompany, testCompany);
    }

    @Test
    @DisplayName("equals - Null comparison: Should return false")
    void equals_ShouldReturnFalseForNull() {
        assertNotEquals(testCompany, null);
    }

    @Test
    @DisplayName("equals - Different class: Should return false")
    void equals_ShouldReturnFalseForDifferentType() {
        assertNotEquals(testCompany, "Some String");
    }

    @Test
    @DisplayName("equals - Same ID: Should return true")
    void equals_ShouldReturnTrueForSameId() {
        Company other = new Company(1L);
        assertEquals(testCompany, other);
    }

    @Test
    @DisplayName("equals - Different ID: Should return false")
    void equals_ShouldReturnFalseForDifferentId() {
        Company other = new Company(2L);
        assertNotEquals(testCompany, other);
    }

    @Test
    @DisplayName("equals - Both IDs null: Should return true")
    void equals_ShouldReturnTrueForBothIdsNull() {
        Company c1 = new Company();
        Company c2 = new Company();
        assertEquals(c1, c2);
    }

    @Test
    @DisplayName("equals - One ID null, other not: Should return false")
    void equals_ShouldReturnFalseWhenOneIdIsNull() {
        Company c1 = new Company(1L);
        Company c2 = new Company();
        assertNotEquals(c1, c2);
        assertNotEquals(c2, c1);
    }


    @Test
    @DisplayName("hashCode - Same ID: Should produce identical code")
    void hashCode_ShouldBeSameForSameId() {
        Company other = new Company(1L);
        assertEquals(testCompany.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Different ID: Should produce different code")
    void hashCode_ShouldBeDifferentForDifferentId() {
        Company other = new Company(2L);
        assertNotEquals(testCompany.hashCode(), other.hashCode());
    }

    @Test
    @DisplayName("hashCode - Null ID: Should produce stable code")
    void hashCode_ShouldBeStableForNullId() {
        Company c1 = new Company();
        Company c2 = new Company();
        assertEquals(c1.hashCode(), c2.hashCode());
    }
}
