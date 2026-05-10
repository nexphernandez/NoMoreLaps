package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for CompanyPersistenceAdapter.
 * Verifies persistence logic for Company, including mapping and repository interaction.
 */
@ExtendWith(MockitoExtension.class)
class CompanyPersistenceAdapterTest {

    @Mock
    private CompanyJpaRepository repository;

    @Mock
    private CompanyMapper mapper;

    @InjectMocks
    private CompanyPersistenceAdapter adapter;

    private Company testCompany;
    private CompanyJpaEntity testEntity;

    @BeforeEach
    void setUp() {
        testCompany = new Company(1L);
        testEntity = new CompanyJpaEntity();
        testEntity.setId(1L);
    }

    @Test
    @DisplayName("findByEmail - Should return company when email exists")
    void shouldReturnCompanyWhenEmailExists() {
        String email = "corp@test.com";
        when(repository.findByEmail(email)).thenReturn(Optional.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testCompany);

        Optional<Company> result = adapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findByEmail - Should return empty when email does not exist")
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        String email = "notfound@test.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<Company> result = adapter.findByEmail(email);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("findByApiKey - Should return company when API key exists")
    void shouldReturnCompanyWhenApiKeyExists() {
        String apiKey = "valid-key";
        when(repository.findByApiKey(apiKey)).thenReturn(Optional.of(testEntity));
        when(mapper.toDomain(testEntity)).thenReturn(testCompany);

        Optional<Company> result = adapter.findByApiKey(apiKey);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findByApiKey - Should return empty when API key does not exist")
    void shouldReturnEmptyWhenApiKeyDoesNotExist() {
        String apiKey = "invalid-key";
        when(repository.findByApiKey(apiKey)).thenReturn(Optional.empty());

        Optional<Company> result = adapter.findByApiKey(apiKey);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("toEntity - Should handle domain company with null User")
    void toEntityShouldHandleNullUser() {
        testCompany.setUser(null);
        when(mapper.toJpaEntity(testCompany)).thenReturn(new CompanyJpaEntity());

        CompanyJpaEntity result = adapter.toEntity(testCompany);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toEntity - Should handle domain company with User having null ID")
    void toEntityShouldHandleUserWithNullId() {
        testCompany.setUser(new User()); // ID is null
        when(mapper.toJpaEntity(testCompany)).thenReturn(new CompanyJpaEntity());

        CompanyJpaEntity result = adapter.toEntity(testCompany);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toEntity - Should map User with valid ID to UserJpaEntity")
    void toEntityShouldMapUserWithId() {
        testCompany.setUser(new User(10L));
        when(mapper.toJpaEntity(testCompany)).thenReturn(new CompanyJpaEntity());

        CompanyJpaEntity result = adapter.toEntity(testCompany);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals(10L, result.getUser().getId());
    }

    @Test
    @DisplayName("toDomain - Should handle JPA entity with null User")
    void toDomainShouldHandleNullUser() {
        testEntity.setUser(null);
        when(mapper.toDomain(testEntity)).thenReturn(new Company(1L));

        Company result = adapter.toDomain(testEntity);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toDomain - Should handle JPA entity with User having null ID")
    void toDomainShouldHandleUserWithNullId() {
        testEntity.setUser(new UserJpaEntity()); // ID is null
        when(mapper.toDomain(testEntity)).thenReturn(new Company(1L));

        Company result = adapter.toDomain(testEntity);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toDomain - Should map UserJpaEntity with valid ID to domain User")
    void toDomainShouldMapUserWithId() {
        UserJpaEntity userEntity = new UserJpaEntity();
        userEntity.setId(10L);
        testEntity.setUser(userEntity);
        when(mapper.toDomain(testEntity)).thenReturn(new Company(1L));

        Company result = adapter.toDomain(testEntity);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals(10L, result.getUser().getId());
    }
}
