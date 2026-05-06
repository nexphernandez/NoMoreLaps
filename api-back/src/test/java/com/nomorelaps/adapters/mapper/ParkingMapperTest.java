package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Method;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.Company;

import org.mapstruct.factory.Mappers;

class ParkingMapperTest {

    private final ParkingMapper parkingMapper = Mappers.getMapper(ParkingMapper.class);

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        ParkingRequest request = new ParkingRequest();
        request.setName("Central Parking");
        request.setAddress("Street 1");
        request.setLatitude(40.0);
        request.setLongitude(-3.0);
        request.setCompanyId(5L);

        Parking domain = parkingMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Central Parking", domain.getName());
        assertEquals("Street 1", domain.getAddress());
        assertEquals(40.0, domain.getLatitude());
        assertEquals(-3.0, domain.getLongitude());
        assertNotNull(domain.getCompany());
        assertEquals(5L, domain.getCompany().getId());
    }

    @Test
    @DisplayName("toDomainFromRequest - Should handle null companyId")
    void shouldHandleNullCompanyId() {
        ParkingRequest request = new ParkingRequest();
        request.setCompanyId(null);

        Parking domain = parkingMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        if (domain.getCompany() != null) {
            assertNull(domain.getCompany().getId());
        }
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        Parking domain = new Parking(1L);
        domain.setName("Central Parking");
        Company company = new Company(500L);
        domain.setCompany(company);

        ParkingResponse response = parkingMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Central Parking", response.getName());
        assertEquals(500L, response.getCompanyId());
    }

    @Test
    @DisplayName("toResponse - Should handle null company in domain")
    void shouldHandleNullCompanyInToResponse() {
        Parking domain = new Parking(1L);
        domain.setCompany(null);

        ParkingResponse response = parkingMapper.toResponse(domain);

        assertNotNull(response);
        assertNull(response.getCompanyId());
    }

    @Test
    @DisplayName("toResponse - Should handle null company ID in domain")
    void shouldHandleNullCompanyIdInToResponse() {
        Parking domain = new Parking(1L);
        Company company = new Company();
        company.setId(null);
        domain.setCompany(company);

        ParkingResponse response = parkingMapper.toResponse(domain);

        assertNotNull(response);
        assertNull(response.getCompanyId());
    }

    @Test
    @DisplayName("ParkingResponse - Should test status setter")
    void shouldTestStatusSetter() {
        ParkingResponse response = new ParkingResponse();
        response.setStatus("Closed");
        assertEquals("Closed", response.getStatus());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        Parking domain = new Parking(1L);
        domain.setName("Central Parking");

        ParkingJpaEntity entity = parkingMapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Central Parking", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        ParkingJpaEntity entity = new ParkingJpaEntity();
        entity.setId(1L);
        entity.setName("Central Parking");

        Parking domain = parkingMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Central Parking", domain.getName());
    }

    @Test
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() throws Exception {
        assertNull(parkingMapper.toDomainFromRequest(null));
        assertNull(parkingMapper.toResponse(null));
        assertNull(parkingMapper.toJpaEntity(null));
        assertNull(parkingMapper.toDomain(null));

        Method method = parkingMapper.getClass().getDeclaredMethod("parkingRequestToCompany", ParkingRequest.class);
        method.setAccessible(true);
        assertNull(method.invoke(parkingMapper, (ParkingRequest) null));

        Method methodId = parkingMapper.getClass().getDeclaredMethod("domainCompanyId", Parking.class);
        methodId.setAccessible(true);
        assertNull(methodId.invoke(parkingMapper, (Parking) null));

        Parking pNullId = new Parking();
        Company cNullId = new Company();
        cNullId.setId(null);
        pNullId.setCompany(cNullId);
        assertNull(methodId.invoke(parkingMapper, pNullId));
        
        Parking pValid = new Parking();
        pValid.setCompany(new Company(777L));
        assertEquals(777L, methodId.invoke(parkingMapper, pValid));
    }
}
