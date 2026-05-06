package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Method;

import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.domain.models.*;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        UserRequest request = new UserRequest();
        request.setName("Alice");
        request.setEmail("alice@test.com");
        request.setCalendarEnable(true);

        User domain = userMapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Alice", domain.getName());
        assertEquals("alice@test.com", domain.getEmail());
        assertTrue(domain.isCalendarEnable());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        User domain = new User(1L);
        domain.setName("Alice");
        domain.setEmail("alice@test.com");

        UserResponse response = userMapper.toResponse(domain);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Alice", response.getName());
        assertEquals("alice@test.com", response.getEmail());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        User domain = new User(1L);
        domain.setName("Alice");

        UserJpaEntity entity = userMapper.toJpaEntity(domain);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Alice", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("Alice");

        User domain = userMapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Alice", domain.getName());
    }

    @Test
    @DisplayName("Null handling - Should return null when input is null")
    void shouldHandleNulls() {
        assertNull(userMapper.toDomainFromRequest(null));
        assertNull(userMapper.toResponse(null));
        assertNull(userMapper.toJpaEntity(null));
        assertNull(userMapper.toDomain(null));
    }

    @Test
    @DisplayName("toJpaEntity - Should map complex nested objects")
    void shouldMapComplexNestedUserToJpaEntity() {
        User user = new User(1L);
        user.setName("Alice");

        Company company = new Company(10L);
        company.setName("Test Company");

        Parking parking = new Parking(100L);
        parking.setName("Test Parking");

        ParkingSpot spot = new ParkingSpot(1000L);
        spot.setNumber(1);
        parking.setParkingSpots(new HashSet<>(Collections.singletonList(spot)));

        DynamicPrice price = new DynamicPrice(2000L);
        price.setMaxPrice(50.0);
        parking.setDynamicPrice(new HashSet<>(Collections.singletonList(price)));

        company.setParkings(new HashSet<>(Collections.singletonList(parking)));
        user.setCompanies(new HashSet<>(Collections.singletonList(company)));

        UserJpaEntity entity = userMapper.toJpaEntity(user);

        assertNotNull(entity);
        assertNotNull(entity.getCompanies());
        assertEquals(1, entity.getCompanies().size());
        
        var companyEntity = entity.getCompanies().iterator().next();
        assertEquals(10L, companyEntity.getId());
        assertNotNull(companyEntity.getParkings());
        
        var parkingEntity = companyEntity.getParkings().iterator().next();
        assertEquals(100L, parkingEntity.getId());
        assertEquals(1, parkingEntity.getParkingSpots().size());
        assertEquals(1, parkingEntity.getDynamicPrice().size());
    }

    @Test
    @DisplayName("Null handling - Should handle null collections in nested objects")
    void shouldHandleNullCollectionsInNestedObjects() {
        User user = new User(1L);
        Company company = new Company(10L);
        company.setParkings(null); 
        user.setCompanies(new HashSet<>(Collections.singletonList(company)));

        UserJpaEntity entity = userMapper.toJpaEntity(user);

        assertNotNull(entity);
        assertNotNull(entity.getCompanies());
        assertNull(entity.getCompanies().iterator().next().getParkings());
    }

    @Test
    @DisplayName("Null handling - Internal MapStruct methods via reflection")
    void shouldHandleNullsInInternalMethods() throws Exception {
        String[] methods = {
            "parkingSpotToParkingSpotJpaEntity",
            "parkingSpotSetToParkingSpotJpaEntitySet",
            "dynamicPriceToDynamicPriceJpaEntity",
            "dynamicPriceSetToDynamicPriceJpaEntitySet",
            "parkingToParkingJpaEntity",
            "parkingSetToParkingJpaEntitySet",
            "companyToCompanyJpaEntity",
            "companySetToCompanyJpaEntitySet"
        };

        for (String methodName : methods) {
            Method method = userMapper.getClass().getDeclaredMethod(methodName, getParamType(methodName));
            method.setAccessible(true);
            assertNull(method.invoke(userMapper, new Object[]{null}), "Method " + methodName + " should return null for null input");
        }
    }

    private Class<?> getParamType(String methodName) {
        if (methodName.toLowerCase().contains("set")) {
            return Set.class;
        }
        if (methodName.startsWith("parkingSpot")) return ParkingSpot.class;
        if (methodName.startsWith("dynamicPrice")) return DynamicPrice.class;
        if (methodName.startsWith("parking")) return Parking.class;
        if (methodName.startsWith("company")) return Company.class;
        return Object.class;
    }
}

