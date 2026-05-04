package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.domain.models.Company;

class CompanyMapperTest {

    private final CompanyMapper companyMapper = new CompanyMapperImpl();


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        CompanyRequest request = new CompanyRequest();
        request.setName("Corp X");
        request.setEmail("corp@x.com");
        request.setCif("B12345678");

        Company domain = companyMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Corp X", domain.getName());
        assertEquals("corp@x.com", domain.getEmail());
        assertEquals("B12345678", domain.getCif());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        Company domain = new Company(1L);
        domain.setName("Corp X");

        CompanyResponse response = companyMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Corp X", response.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        Company domain = new Company(1L);
        domain.setName("Corp X");

        CompanyJpaEntity entity = companyMapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Corp X", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        entity.setId(1L);
        entity.setName("Corp X");

        Company domain = companyMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Corp X", domain.getName());
    }

    @Test
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() {
        assertNull(companyMapper.toDomainFromRequest(null));
        assertNull(companyMapper.toResponse(null));
        assertNull(companyMapper.toJpaEntity(null));
        assertNull(companyMapper.toDomain(null));
    }
}

