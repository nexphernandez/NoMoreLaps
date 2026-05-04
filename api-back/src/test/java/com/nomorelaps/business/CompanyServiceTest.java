package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.ICompanyPersistenceAdapter;
import com.nomorelaps.domain.models.Company;

/**
 * Unit tests for CompanyService covering all business methods.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class CompanyServiceTest {

    @Mock
    private ICompanyPersistenceAdapter persistencePort;

    @InjectMocks
    private CompanyService companyService;

    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company(1L);
        testCompany.setName("Parking Corp");
        testCompany.setEmail("parking@corp.com");
    }

    // ── create ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create - Should save company when email is not registered")
    void shouldCreateCompanySuccessfully() {
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenReturn(testCompany);

        Company result = companyService.create(testCompany);

        assertNotNull(result);
        assertEquals("Parking Corp", result.getName());
        verify(persistencePort).save(testCompany);
    }

    @Test
    @DisplayName("create - Should throw when email already registered")
    void shouldThrowWhenCompanyEmailExists() {
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.of(testCompany));

        assertThrows(IllegalArgumentException.class, () -> companyService.create(testCompany));
        verify(persistencePort, never()).save(any());
    }

    // ── findById ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - Should return company when found")
    void shouldFindCompanyById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testCompany));

        Optional<Company> result = companyService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findById - Should return empty when not found")
    void shouldReturnEmptyWhenCompanyNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());

        Optional<Company> result = companyService.findById(99L);

        assertFalse(result.isPresent());
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - Should return all companies")
    void shouldFindAllCompanies() {
        Company c2 = new Company(2L);
        c2.setName("Another Corp");
        when(persistencePort.findAll()).thenReturn(Arrays.asList(testCompany, c2));

        List<Company> result = companyService.findAll();

        assertEquals(2, result.size());
    }

    // ── findByEmail ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("findByEmail - Should return company when found")
    void shouldFindCompanyByEmail() {
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.of(testCompany));

        Optional<Company> result = companyService.findByEmail("parking@corp.com");

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("findByEmail - Should return empty when not found")
    void shouldReturnEmptyWhenEmailNotFound() {
        when(persistencePort.findByEmail("unknown@test.com")).thenReturn(Optional.empty());

        Optional<Company> result = companyService.findByEmail("unknown@test.com");

        assertFalse(result.isPresent());
    }

    // ── findByApiKey ──────────────────────────────────────────────────────────

    @Test
    @DisplayName("findByApiKey - Should return company by API key")
    void shouldFindCompanyByApiKey() {
        when(persistencePort.findByApiKey("secret-key-123")).thenReturn(Optional.of(testCompany));

        Optional<Company> result = companyService.findByApiKey("secret-key-123");

        assertTrue(result.isPresent());
        assertEquals("Parking Corp", result.get().getName());
    }

    @Test
    @DisplayName("findByApiKey - Should return empty when API key unknown")
    void shouldReturnEmptyWhenApiKeyUnknown() {
        when(persistencePort.findByApiKey("wrong-key")).thenReturn(Optional.empty());

        Optional<Company> result = companyService.findByApiKey("wrong-key");

        assertFalse(result.isPresent());
    }

    // ── update ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update - Should delegate to persistence save")
    void shouldUpdateCompany() {
        testCompany.setName("Updated Corp");
        when(persistencePort.save(testCompany)).thenReturn(testCompany);

        Company result = companyService.update(testCompany);

        assertEquals("Updated Corp", result.getName());
        verify(persistencePort).save(testCompany);
    }

    // ── deleteById ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById - Should call persistence deleteById")
    void shouldDeleteCompanyById() {
        doNothing().when(persistencePort).deleteById(1L);

        companyService.deleteById(1L);

        verify(persistencePort).deleteById(1L);
    }
}
