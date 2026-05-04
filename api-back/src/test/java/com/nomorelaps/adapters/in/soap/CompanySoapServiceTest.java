package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CompanySoapServiceTest {

    private ICompanyService companyService;
    private CompanyMapper companyMapper;
    private CompanySoapService companySoapService;

    @BeforeEach
    void setUp() {
        companyService = mock(ICompanyService.class);
        companyMapper = mock(CompanyMapper.class);
        companySoapService = new CompanySoapService(companyService, companyMapper);
    }

    @Test
    @DisplayName("findAll - Should return list of company responses")
    void shouldReturnListOfCompanyResponses() {
        Company company = new Company(1L);
        CompanyResponse response = new CompanyResponse();
        response.setId(1L);

        when(companyService.findAll()).thenReturn(Collections.singletonList(company));
        when(companyMapper.toResponse(company)).thenReturn(response);

        List<CompanyResponse> result = companySoapService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());

        verify(companyService, times(1)).findAll();
        verify(companyMapper, times(1)).toResponse(company);
    }

    @Test
    @DisplayName("findById - Should return company response when found")
    void shouldReturnCompanyResponseWhenFound() {
        Company company = new Company(1L);
        CompanyResponse response = new CompanyResponse();
        response.setId(1L);

        when(companyService.findById(1L)).thenReturn(Optional.of(company));
        when(companyMapper.toResponse(company)).thenReturn(response);

        CompanyResponse result = companySoapService.findById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());

        verify(companyService, times(1)).findById(1L);
        verify(companyMapper, times(1)).toResponse(company);
    }

    @Test
    @DisplayName("findById - Should return null when not found")
    void shouldReturnNullWhenNotFound() {
        when(companyService.findById(1L)).thenReturn(Optional.empty());

        CompanyResponse result = companySoapService.findById(1L);

        assertNull(result);

        verify(companyService, times(1)).findById(1L);
        verify(companyMapper, never()).toResponse(any());
    }
}
