package com.nomorelaps.adapters.mapper;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;

import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.domain.models.*;

/**
 * Unit tests for UserMapper.
 * Verifies mapping between User domain models, API requests/responses, and JPA entities.
 * Handles complex nested collections for Companies, Parkings, and ParkingSpots.
 */
class UserMapperTest {

    private final UserMapper mapper = Mappers.getMapper(UserMapper.class);
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L);
        testUser.setName("Alice");
        testUser.setEmail("alice@test.com");
        testUser.setCalendarEnable(true);
    }

    @Test
    @DisplayName("toDomainFromRequest - Should map request to domain")
    void shouldMapRequestToDomain() {
        UserRequest request = new UserRequest();
        request.setName("Bob");
        request.setEmail("bob@test.com");
        request.setCalendarEnable(false);

        User domain = mapper.toDomainFromRequest(request);

        assertNotNull(domain);
        assertEquals("Bob", domain.getName());
        assertEquals("bob@test.com", domain.getEmail());
        assertFalse(domain.isCalendarEnable());
    }

    @Test
    @DisplayName("toResponse - Should map domain to response")
    void shouldMapDomainToResponse() {
        UserResponse response = mapper.toResponse(testUser);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Alice", response.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map domain to entity")
    void shouldMapDomainToEntity() {
        UserJpaEntity entity = mapper.toJpaEntity(testUser);

        assertNotNull(entity);
        assertEquals(1L, entity.getId());
        assertEquals("Alice", entity.getName());
    }

    @Test
    @DisplayName("toDomain - Should map entity to domain")
    void shouldMapEntityToDomain() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setId(1L);
        entity.setName("Charlie");

        User domain = mapper.toDomain(entity);

        assertNotNull(domain);
        assertEquals(1L, domain.getId());
        assertEquals("Charlie", domain.getName());
    }

    @Test
    @DisplayName("toJpaEntity - Should map complex nested objects")
    void shouldMapComplexNestedUser() {
        Company company = new Company(10L);
        Parking parking = new Parking(100L);
        ParkingSpot spot = new ParkingSpot(1000L);
        spot.setNumber(1);
        
        parking.setParkingSpots(new HashSet<>(Collections.singletonList(spot)));
        company.setParkings(new HashSet<>(Collections.singletonList(parking)));
        testUser.setCompanies(new HashSet<>(Collections.singletonList(company)));

        UserJpaEntity entity = mapper.toJpaEntity(testUser);

        assertNotNull(entity);
        assertEquals(1, entity.getCompanies().size());
        var companyEntity = entity.getCompanies().iterator().next();
        assertEquals(10L, companyEntity.getId());
        assertEquals(1, companyEntity.getParkings().size());
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
    @DisplayName("Internal: companyToCompanyJpaEntity - Should handle null via reflection")
    void shouldHandleNullInternalCompanyMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("companyToCompanyJpaEntity", Company.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (Company) null));
    }

    @Test
    @DisplayName("Internal: companySetToCompanyJpaEntitySet - Should handle null via reflection")
    void shouldHandleNullInternalCompanySetMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("companySetToCompanyJpaEntitySet", Set.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (Set<?>) null));
    }

    @Test
    @DisplayName("Internal: parkingToParkingJpaEntity - Should handle null via reflection")
    void shouldHandleNullInternalParkingMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingToParkingJpaEntity", Parking.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (Parking) null));
    }

    @Test
    @DisplayName("Internal: parkingSetToParkingJpaEntitySet - Should handle null via reflection")
    void shouldHandleNullInternalParkingSetMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingSetToParkingJpaEntitySet", Set.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (Set<?>) null));
    }

    @Test
    @DisplayName("Internal: dynamicPriceToDynamicPriceJpaEntity - Should handle null via reflection")
    void shouldHandleNullInternalDynamicPriceMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("dynamicPriceToDynamicPriceJpaEntity", DynamicPrice.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (DynamicPrice) null));
    }

    @Test
    @DisplayName("Internal: dynamicPriceToDynamicPriceJpaEntity - Should map full object via reflection")
    void shouldMapFullInternalDynamicPrice() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("dynamicPriceToDynamicPriceJpaEntity", DynamicPrice.class);
        method.setAccessible(true);
        
        DynamicPrice price = new DynamicPrice(55L);
        price.setDayOfWeek(1);
        price.setMinPrice(10.0);
        
        Object result = method.invoke(mapper, price);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Internal: dynamicPriceSetToDynamicPriceJpaEntitySet - Should handle null via reflection")
    void shouldHandleNullInternalDynamicPriceSetMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("dynamicPriceSetToDynamicPriceJpaEntitySet", Set.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (Set<?>) null));
    }

    @Test
    @DisplayName("Internal: dynamicPriceSetToDynamicPriceJpaEntitySet - Should map set via reflection")
    void shouldMapInternalDynamicPriceSet() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("dynamicPriceSetToDynamicPriceJpaEntitySet", Set.class);
        method.setAccessible(true);
        
        Set<DynamicPrice> set = new HashSet<>();
        set.add(new DynamicPrice(1L));
        
        Object result = method.invoke(mapper, set);
        assertNotNull(result);
        assertTrue(((Set<?>) result).size() > 0);
    }

    @Test
    @DisplayName("Internal: parkingSpotToParkingSpotJpaEntity - Should handle null via reflection")
    void shouldHandleNullInternalParkingSpotMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingSpotToParkingSpotJpaEntity", ParkingSpot.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (ParkingSpot) null));
    }

    @Test
    @DisplayName("Internal: parkingSpotToParkingSpotJpaEntity - Should map full object via reflection")
    void shouldMapFullInternalParkingSpot() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingSpotToParkingSpotJpaEntity", ParkingSpot.class);
        method.setAccessible(true);
        
        ParkingSpot spot = new ParkingSpot(77L);
        spot.setNumber(123);
        
        Object result = method.invoke(mapper, spot);
        assertNotNull(result);
    }

    @Test
    @DisplayName("Internal: parkingSpotSetToParkingSpotJpaEntitySet - Should handle null via reflection")
    void shouldHandleNullInternalParkingSpotSetMapping() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingSpotSetToParkingSpotJpaEntitySet", Set.class);
        method.setAccessible(true);
        assertNull(method.invoke(mapper, (Set<?>) null));
    }

    @Test
    @DisplayName("Internal: parkingSpotSetToParkingSpotJpaEntitySet - Should map set via reflection")
    void shouldMapInternalParkingSpotSet() throws Exception {
        Method method = mapper.getClass().getDeclaredMethod("parkingSpotSetToParkingSpotJpaEntitySet", Set.class);
        method.setAccessible(true);
        
        Set<ParkingSpot> set = new HashSet<>();
        set.add(new ParkingSpot(1L));
        
        Object result = method.invoke(mapper, set);
        assertNotNull(result);
        assertTrue(((Set<?>) result).size() > 0);
    }
}
