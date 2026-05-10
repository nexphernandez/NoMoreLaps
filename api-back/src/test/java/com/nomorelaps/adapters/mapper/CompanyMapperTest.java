package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.domain.models.Company;

/**
 * Unit tests for CompanyMapper.
 * Verifies mapping between Company domain models, API requests/responses, and JPA entities.
 */
class CompanyMapperTest {

    private final CompanyMapper mapper = Mappers.getMapper(CompanyMapper.class);
    private Company testCompany;

    @BeforeEach
    void setUp() {
        testCompany = new Company(1L);
        testCompany.setName("Original Corp");
        testCompany.setEmail("info@corp.com");
        testCompany.setCif("B12345678");
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        CompanyRequest request = new CompanyRequest();
        request.setName("Corp X");
        request.setEmail("corp@x.com");
        request.setCif("B12345678");

        Company domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Corp X", domain.getName());
        assertEquals("corp@x.com", domain.getEmail());
        assertEquals("B12345678", domain.getCif());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        CompanyResponse response = mapper.toResponse(testCompany);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Original Corp", response.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        CompanyJpaEntity entity = mapper.toJpaEntity(testCompany);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Original Corp", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        entity.setId(1L);
        entity.setName("Corp X");

        Company domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Corp X", domain.getName());
    }

    @Test
    @DisplayName("toDomainFromRequest - Should return null when input is null")
    void shouldReturnNullWhenRequestIsNull() {
        assertNull(mapper.toDomainFromRequest(null));
    }

    @Test
    @DisplayName("toResponse - Should return null when input is null")
    void shouldReturnNullWhenDomainIsNullForResponse() {
        assertNull(mapper.toResponse(null));
    }

    @Test
    @DisplayName("toJpaEntity - Should return null when input is null")
    void shouldReturnNullWhenDomainIsNullForJpa() {
        assertNull(mapper.toJpaEntity(null));
    }

    @Test
    @DisplayName("toDomain - Should return null when input is null")
    void shouldReturnNullWhenJpaIsNull() {
        assertNull(mapper.toDomain(null));
    }
}
