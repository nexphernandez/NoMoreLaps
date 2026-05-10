package com.nomorelaps.adapters.in.soap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

/**
 * Unit tests for CompanySoapService.
 * Validates retrieval of company information via SOAP adapter.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
class CompanySoapServiceTest {

    @MockitoBean
    private ICompanyService companyService;

    @MockitoSpyBean
    private CompanyMapper companyMapper;

    @Autowired
    private CompanySoapService companySoapService;

    private Company sampleCompany;

    @BeforeEach
    void setUp() {
        sampleCompany = new Company(1L);
        sampleCompany.setName("Test Company");
    }

    @Test
    @DisplayName("findAll - Success: Should return list of all companies")
    void shouldReturnAllCompaniesSuccessfully() {
        when(companyService.findAll()).thenReturn(List.of(sampleCompany));

        List<CompanyResponse> result = companySoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        assertEquals("Test Company", result.get(0).getName());
        verify(companyService).findAll();
    }

    @Test
    @DisplayName("findById - Success: Should return company details when found")
    void shouldReturnCompanyByIdSuccessfully() {
        when(companyService.findById(1L)).thenReturn(Optional.of(sampleCompany));

        CompanyResponse result = companySoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals("Test Company", result.getName());
        verify(companyService).findById(1L);
    }

    @Test
    @DisplayName("findById - Failure: Should return null when company does not exist")
    void shouldReturnNullWhenCompanyNotFound() {
        when(companyService.findById(99L)).thenReturn(Optional.empty());

        CompanyResponse result = companySoapService.findById(99L);

        assertNull(result);
        verify(companyService).findById(99L);
        verify(companyMapper, never()).toResponse(any());
    }
}
