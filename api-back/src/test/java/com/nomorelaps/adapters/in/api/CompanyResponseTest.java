package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CompanyResponse DTO.
 * Separates constructor, accessors, and equality logic into granular tests.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class CompanyResponseTest {

    private CompanyResponse companyResponse;
    private LocalDateTime registerDate;

    @BeforeEach
    void setUp() {
        companyResponse = new CompanyResponse();
        registerDate = LocalDateTime.now();
    }

    @Test
    @DisplayName("Constructor(id) - Should initialize with ID")
    void shouldInitializeWithIdConstructor() {
        CompanyResponse idOnlyResponse = new CompanyResponse(100L);
        assertEquals(100L, idOnlyResponse.getId());
    }

    @Test
    @DisplayName("Full Constructor - Should correctly map all fields")
    void shouldInitializeWithFullConstructor() {
        CompanyResponse fullResponse = new CompanyResponse(1L, "Parking Ltd", "key-123", "555-4444", "admin@parking.ltd", "A88888888", registerDate);

        assertEquals(1L, fullResponse.getId());
        assertEquals("Parking Ltd", fullResponse.getName());
        assertEquals("key-123", fullResponse.getApiKey());
        assertEquals("555-4444", fullResponse.getPhone());
        assertEquals("admin@parking.ltd", fullResponse.getEmail());
        assertEquals("A88888888", fullResponse.getCif());
        assertEquals(registerDate, fullResponse.getRegisterDay());
    }

    @Test
    @DisplayName("name - Should set and get the name")
    void shouldSetAndGetName() {
        companyResponse.setName("Global Parking");
        assertEquals("Global Parking", companyResponse.getName());
    }

    @Test
    @DisplayName("apiKey - Should set and get the API key")
    void shouldSetAndGetApiKey() {
        companyResponse.setApiKey("nml_prod_777");
        assertEquals("nml_prod_777", companyResponse.getApiKey());
    }

    @Test
    @DisplayName("phone - Should set and get the phone")
    void shouldSetAndGetPhone() {
        companyResponse.setPhone("911-222-333");
        assertEquals("911-222-333", companyResponse.getPhone());
    }

    @Test
    @DisplayName("email - Should set and get the email")
    void shouldSetAndGetEmail() {
        companyResponse.setEmail("support@global.com");
        assertEquals("support@global.com", companyResponse.getEmail());
    }

    @Test
    @DisplayName("cif - Should set and get the CIF")
    void shouldSetAndGetCif() {
        companyResponse.setCif("B55555555");
        assertEquals("B55555555", companyResponse.getCif());
    }

    @Test
    @DisplayName("registerDay - Should set and get the registration date")
    void shouldSetAndGetRegisterDay() {
        companyResponse.setRegisterDay(registerDate);
        assertEquals(registerDate, companyResponse.getRegisterDay());
    }

    @Test
    @DisplayName("equals - Should be equal for same ID")
    void shouldBeEqualForSameId() {
        CompanyResponse firstCompany = new CompanyResponse(10L);
        CompanyResponse secondCompany = new CompanyResponse(10L);
        CompanyResponse thirdCompany = new CompanyResponse(20L);

        assertEquals(firstCompany, firstCompany, "Should be equal to itself");
        assertEquals(firstCompany, secondCompany, "Should be equal if IDs match");
        assertNotEquals(firstCompany, thirdCompany, "Should not be equal if IDs differ");
        assertNotEquals(firstCompany, null, "Should not be equal to null");
        assertNotEquals(firstCompany, new Object(), "Should not be equal to other types");
    }

    @Test
    @DisplayName("hashCode - Should be consistent for same ID")
    void shouldHaveConsistentHashCode() {
        CompanyResponse firstCompany = new CompanyResponse(10L);
        CompanyResponse secondCompany = new CompanyResponse(10L);

        assertEquals(firstCompany.hashCode(), secondCompany.hashCode(), "Same ID should produce same hash code");
    }
}
