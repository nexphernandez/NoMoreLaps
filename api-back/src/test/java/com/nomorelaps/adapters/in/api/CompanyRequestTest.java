package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for CompanyRequest DTO.
 * Verifies that company details are correctly handled via constructors and accessors.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
class CompanyRequestTest {

    private CompanyRequest companyRequest;

    @BeforeEach
    void setUp() {
        companyRequest = new CompanyRequest();
    }

    @Test
    @DisplayName("Constructor - Should correctly initialize all fields")
    void shouldInitializeWithFullConstructor() {
        CompanyRequest fullRequest = new CompanyRequest("Parking Corp", "secret-api-key", "pass123", "555-0000", "info@parkingcorp.com", "B12345678");

        assertEquals("Parking Corp", fullRequest.getName());
        assertEquals("secret-api-key", fullRequest.getApiKey());
        assertEquals("pass123", fullRequest.getPassword());
        assertEquals("555-0000", fullRequest.getPhone());
        assertEquals("info@parkingcorp.com", fullRequest.getEmail());
        assertEquals("B12345678", fullRequest.getCif());
    }

    @Test
    @DisplayName("name - Should set and get the company name")
    void shouldSetAndGetName() {
        companyRequest.setName("Central Parking");
        assertEquals("Central Parking", companyRequest.getName());
    }

    @Test
    @DisplayName("apiKey - Should set and get the API key")
    void shouldSetAndGetApiKey() {
        companyRequest.setApiKey("api-777-key");
        assertEquals("api-777-key", companyRequest.getApiKey());
    }

    @Test
    @DisplayName("password - Should set and get the password")
    void shouldSetAndGetPassword() {
        companyRequest.setPassword("securePassword!");
        assertEquals("securePassword!", companyRequest.getPassword());
    }

    @Test
    @DisplayName("phone - Should set and get the phone number")
    void shouldSetAndGetPhone() {
        companyRequest.setPhone("123-456-789");
        assertEquals("123-456-789", companyRequest.getPhone());
    }

    @Test
    @DisplayName("email - Should set and get the email")
    void shouldSetAndGetEmail() {
        companyRequest.setEmail("contact@central.com");
        assertEquals("contact@central.com", companyRequest.getEmail());
    }

    @Test
    @DisplayName("cif - Should set and get the CIF")
    void shouldSetAndGetCif() {
        companyRequest.setCif("A99999999");
        assertEquals("A99999999", companyRequest.getCif());
    }
}
