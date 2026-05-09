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


    @Test
    @DisplayName("create - Should save company and generate API key when email is not registered")
    void shouldCreateCompanySuccessfully() {
        testCompany.setApiKey(null);
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.create(testCompany);

        assertNotNull(result);
        assertNotNull(result.getApiKey());
        assertTrue(result.getApiKey().startsWith("nml_live_"));
        verify(persistencePort).save(testCompany);
    }

    @Test
    @DisplayName("create - Should not generate API key if it already exists")
    void shouldNotGenerateApiKeyIfAlreadyPresent() {
        testCompany.setApiKey("existing-key");
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.create(testCompany);

        assertEquals("existing-key", result.getApiKey());
    }

    @Test
    @DisplayName("create - Should throw when email already registered")
    void shouldThrowWhenCompanyEmailExists() {
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.of(testCompany));

        assertThrows(IllegalArgumentException.class, () -> companyService.create(testCompany));
        verify(persistencePort, never()).save(any());
    }


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


    @Test
    @DisplayName("findAll - Should return all companies")
    void shouldFindAllCompanies() {
        Company c2 = new Company(2L);
        c2.setName("Another Corp");
        when(persistencePort.findAll()).thenReturn(Arrays.asList(testCompany, c2));

        List<Company> result = companyService.findAll();

        assertEquals(2, result.size());
    }


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


    @Test
    @DisplayName("create - Should generate API key when it is an empty string")
    void shouldGenerateApiKeyIfEmpty() {
        testCompany.setApiKey("");
        when(persistencePort.findByEmail("parking@corp.com")).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.create(testCompany);

        assertNotNull(result.getApiKey());
        assertTrue(result.getApiKey().startsWith("nml_live_"));
    }

    @Test
    @DisplayName("update - Should throw IllegalArgumentException when company not found")
    void shouldThrowWhenUpdatingNonExistentCompany() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> companyService.update(testCompany));
    }

    @Test
    @DisplayName("update - Should only update name if others are null")
    void shouldUpdateOnlyName() {
        Company existing = new Company(1L);
        existing.setName("Old");
        existing.setEmail("old@test.com");
        
        Company req = new Company(1L);
        req.setName("New");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(req);
        assertEquals("New", result.getName());
        assertEquals("old@test.com", result.getEmail());
    }

    @Test
    @DisplayName("update - Should only update phone if others are null")
    void shouldUpdateOnlyPhone() {
        Company existing = new Company(1L);
        existing.setPhone("111");
        Company req = new Company(1L);
        req.setPhone("222");
        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);
        Company result = companyService.update(req);
        assertEquals("222", result.getPhone());
    }

    @Test
    @DisplayName("update - Should only update email if others are null")
    void shouldUpdateOnlyEmail() {
        Company existing = new Company(1L);
        existing.setEmail("old@test.com");
        Company req = new Company(1L);
        req.setEmail("new@test.com");
        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);
        Company result = companyService.update(req);
        assertEquals("new@test.com", result.getEmail());
    }

    @Test
    @DisplayName("update - Should only update cif if others are null")
    void shouldUpdateOnlyCif() {
        Company existing = new Company(1L);
        existing.setCif("A1");
        Company req = new Company(1L);
        req.setCif("B2");
        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);
        Company result = companyService.update(req);
        assertEquals("B2", result.getCif());
    }

    @Test
    @DisplayName("update - Should update password only if not blank")
    void shouldUpdatePasswordOnlyIfNotBlank() {
        Company existing = new Company(1L);
        existing.setPassword("old");
        
        Company req = new Company(1L);
        req.setPassword("   "); // blank

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(req);
        assertEquals("old", result.getPassword());
        
        req.setPassword("newPass");
        result = companyService.update(req);
        assertEquals("newPass", result.getPassword());
    }

    @Test
    @DisplayName("regenerateApiKey - Should throw when not found")
    void shouldThrowWhenRegeneratingNonExistentCompany() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> companyService.regenerateApiKey(1L));
    }

    @Test
    @DisplayName("regenerateApiKey - Should generate a new key and save when found")
    void shouldRegenerateApiKey() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testCompany));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.regenerateApiKey(1L);

        assertNotNull(result.getApiKey());
        assertTrue(result.getApiKey().startsWith("nml_live_"));
        verify(persistencePort).save(testCompany);
    }

    @Test
    @DisplayName("deleteById - Should call persistence deleteById")
    void shouldDeleteCompanyById() {
        doNothing().when(persistencePort).deleteById(1L);
        companyService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }
}
