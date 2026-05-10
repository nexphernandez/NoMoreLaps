package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mapstruct.factory.Mappers;
import org.springframework.test.util.ReflectionTestUtils;

import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.Company;

/**
 * Unit tests for ParkingMapper.
 * Verifies mapping between Parking domain models, API requests/responses, and JPA entities.
 * Includes validation for nested Company relationships and internal helper methods.
 */
class ParkingMapperTest {

    private final ParkingMapper mapper = Mappers.getMapper(ParkingMapper.class);

    @Mock
    private CompanyMapper companyMapper;

    private Parking testParking;
    private Company testCompany;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ReflectionTestUtils.setField(mapper, "companyMapper", companyMapper);
        
        testCompany = new Company(500L);
        testCompany.setName("Test Corp");
        
        testParking = new Parking(1L);
        testParking.setName("Downtown Parking");
        testParking.setAddress("123 Main St");
        testParking.setLatitude(40.4168);
        testParking.setLongitude(-3.7038);
        testParking.setCompany(testCompany);
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain with company")
    void shouldMapRequestToDomain() {
        ParkingRequest request = new ParkingRequest();
        request.setName("Airport Parking");
        request.setAddress("Gate 1");
        request.setLatitude(40.0);
        request.setLongitude(-3.0);
        request.setCompanyId(99L);

        Parking domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Airport Parking", domain.getName());
        assertEquals("Gate 1", domain.getAddress());
        assertNotNull(domain.getCompany());
        assertEquals(99L, domain.getCompany().getId());
    }

    @Test
    @DisplayName("toDomainFromRequest - Should handle null companyId")
    void shouldHandleNullCompanyIdInRequest() {
        ParkingRequest request = new ParkingRequest();
        request.setCompanyId(null);

        Parking domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        if (domain.getCompany() != null) {
            assertNull(domain.getCompany().getId());
        }
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        ParkingResponse response = mapper.toResponse(testParking);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Downtown Parking", response.getName());
        assertEquals(500L, response.getCompanyId());
    }

    @Test
    @DisplayName("toResponse - Should handle null company in domain")
    void shouldHandleNullCompanyInToResponse() {
        testParking.setCompany(null);
        ParkingResponse response = mapper.toResponse(testParking);

        assertNotNull(response);
        assertNull(response.getCompanyId());
    }

    @Test
    @DisplayName("toResponse - Should handle null company ID in domain")
    void shouldHandleNullCompanyIdInToResponse() {
        testCompany.setId(null);
        testParking.setCompany(testCompany);
        
        ParkingResponse response = mapper.toResponse(testParking);

        assertNotNull(response);
        assertNull(response.getCompanyId());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        ParkingJpaEntity entity = mapper.toJpaEntity(testParking);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Downtown Parking", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        ParkingJpaEntity entity = new ParkingJpaEntity();
        entity.setId(1L);
        entity.setName("Suburban Lot");

        Parking domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Suburban Lot", domain.getName());
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

    @Test
    @DisplayName("Internal: parkingRequestToCompany - Should handle null via reflection")
    void shouldHandleNullInternalCompanyMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingRequestToCompany", ParkingRequest.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (ParkingRequest) null));
    }

    @Test
    @DisplayName("Internal: domainCompanyId - Should handle nulls via reflection")
    void shouldHandleNullInternalCompanyIdMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("domainCompanyId", Parking.class);
        method.setAccessible(true);
        
        assertNull(method.invoke(mapper, (Parking) null));
        
        testParking.setCompany(null);
        assertNull(method.invoke(mapper, testParking));

        testCompany.setId(null);
        testParking.setCompany(testCompany);
        assertNull(method.invoke(mapper, testParking));

        testCompany.setId(777L);
        assertEquals(777L, method.invoke(mapper, testParking));
    }
}
