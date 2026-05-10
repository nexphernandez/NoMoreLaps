package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

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
 * Unit tests for CompanyService.
 * Verifies business logic for company management, including API key generation
 * and partial updates.
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
    @DisplayName("create - Should save company and generate API key")
    void shouldCreateCompanySuccessfully() {
        testCompany.setApiKey(null);
        when(persistencePort.findByEmail(testCompany.getEmail())).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.create(testCompany);

        assertNotNull(result);
        assertNotNull(result.getApiKey());
        assertTrue(result.getApiKey().startsWith("nml_live_"));
        verify(persistencePort).save(testCompany);
    }

    @Test
    @DisplayName("create - Should not overwrite existing API key")
    void shouldNotOverwriteExistingApiKey() {
        testCompany.setApiKey("existing-key");
        when(persistencePort.findByEmail(testCompany.getEmail())).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.create(testCompany);

        assertEquals("existing-key", result.getApiKey());
    }

    @Test
    @DisplayName("create - Should throw when email is already registered")
    void shouldThrowWhenEmailExists() {
        when(persistencePort.findByEmail(testCompany.getEmail())).thenReturn(Optional.of(testCompany));

        assertThrows(IllegalArgumentException.class, () -> companyService.create(testCompany));
    }

    @Test
    @DisplayName("update - Should update name if provided")
    void shouldUpdateName() {
        Company existing = new Company(1L);
        existing.setName("Old Name");
        Company request = new Company(1L);
        request.setName("New Name");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(request);
        assertEquals("New Name", result.getName());
    }

    @Test
    @DisplayName("update - Should update phone if provided")
    void shouldUpdatePhone() {
        Company existing = new Company(1L);
        existing.setPhone("111");
        Company request = new Company(1L);
        request.setPhone("222");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(request);
        assertEquals("222", result.getPhone());
    }

    @Test
    @DisplayName("update - Should update email if provided")
    void shouldUpdateEmail() {
        Company existing = new Company(1L);
        existing.setEmail("old@test.com");
        Company request = new Company(1L);
        request.setEmail("new@test.com");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(request);
        assertEquals("new@test.com", result.getEmail());
    }

    @Test
    @DisplayName("update - Should update CIF if provided")
    void shouldUpdateCif() {
        Company existing = new Company(1L);
        existing.setCif("A1");
        Company request = new Company(1L);
        request.setCif("B2");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(request);
        assertEquals("B2", result.getCif());
    }

    @Test
    @DisplayName("update - Should update password if provided and not blank")
    void shouldUpdatePassword() {
        Company existing = new Company(1L);
        existing.setPassword("old-pass");
        Company request = new Company(1L);
        request.setPassword("new-pass");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(request);
        assertEquals("new-pass", result.getPassword());
    }

    @Test
    @DisplayName("update - Should not update password if blank")
    void shouldNotUpdatePasswordIfBlank() {
        Company existing = new Company(1L);
        existing.setPassword("keep-this");
        Company request = new Company(1L);
        request.setPassword("   ");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existing));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.update(request);
        assertEquals("keep-this", result.getPassword());
    }

    @Test
    @DisplayName("update - Should throw exception if company not found")
    void shouldThrowIfNotFoundOnUpdate() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> companyService.update(testCompany));
    }

    @Test
    @DisplayName("regenerateApiKey - Should generate new key for existing company")
    void shouldRegenerateApiKey() {
        testCompany.setApiKey("old-key");
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testCompany));
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.regenerateApiKey(1L);

        assertNotEquals("old-key", result.getApiKey());
        assertTrue(result.getApiKey().startsWith("nml_live_"));
    }

    @Test
    @DisplayName("regenerateApiKey - Should throw if company not found")
    void shouldThrowIfNotFoundOnRegenerate() {
        when(persistencePort.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> companyService.regenerateApiKey(1L));
    }

    @Test
    @DisplayName("findById - Should return company if exists")
    void shouldReturnCompanyById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testCompany));
        assertTrue(companyService.findById(1L).isPresent());
    }

    @Test
    @DisplayName("findAll - Should return all companies")
    void shouldReturnAllCompanies() {
        when(persistencePort.findAll()).thenReturn(List.of(testCompany));
        assertEquals(1, companyService.findAll().size());
    }

    @Test
    @DisplayName("deleteById - Should call persistence port")
    void shouldDeleteById() {
        companyService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
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
    void shouldGenerateApiKeyWhenEmptyString() {
        testCompany.setApiKey("");
        when(persistencePort.findByEmail(testCompany.getEmail())).thenReturn(Optional.empty());
        when(persistencePort.save(any(Company.class))).thenAnswer(i -> i.getArguments()[0]);

        Company result = companyService.create(testCompany);

        assertNotNull(result.getApiKey());
        assertTrue(result.getApiKey().startsWith("nml_live_"));
        assertNotEquals("", result.getApiKey());
    }
}
