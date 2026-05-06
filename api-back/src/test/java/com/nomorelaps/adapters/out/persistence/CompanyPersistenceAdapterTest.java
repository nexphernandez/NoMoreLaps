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
import com.nomorelaps.adapters.out.persistence.repository.CompanyJpaRepository;
import com.nomorelaps.domain.models.Company;
import com.nomorelaps.domain.models.User;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;

@ExtendWith(MockitoExtension.class)
class CompanyPersistenceAdapterTest {

    @Mock
    private CompanyJpaRepository repository;

    @Mock
    private CompanyMapper mapper;

    @InjectMocks
    private CompanyPersistenceAdapter adapter;

    private Company company;
    private CompanyJpaEntity entity;

    @BeforeEach
    void setUp() {
        company = new Company(1L);
        entity = new CompanyJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByEmail - Should return company")
    void shouldReturnByEmail() {
        when(repository.findByEmail("corp@test.com")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(company);

        Optional<Company> result = adapter.findByEmail("corp@test.com");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findByApiKey - Should return company")
    void shouldReturnByApiKey() {
        when(repository.findByApiKey("api-key")).thenReturn(Optional.of(entity));
        when(mapper.toDomain(entity)).thenReturn(company);

        Optional<Company> result = adapter.findByApiKey("api-key");

        assertTrue(result.isPresent());
    }

    @Test
    @DisplayName("toEntity - Should handle null user")
    void toEntityShouldHandleNullUser() {
        Company domain = new Company(1L);
        domain.setUser(null);
        when(mapper.toJpaEntity(domain)).thenReturn(new CompanyJpaEntity());

        CompanyJpaEntity result = adapter.toEntity(domain);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toEntity - Should handle user with null ID")
    void toEntityShouldHandleUserWithNullId() {
        Company domain = new Company(1L);
        domain.setUser(new User());
        when(mapper.toJpaEntity(domain)).thenReturn(new CompanyJpaEntity());

        CompanyJpaEntity result = adapter.toEntity(domain);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toEntity - Should map user with valid ID")
    void toEntityShouldMapUserWithId() {
        Company domain = new Company(1L);
        domain.setUser(new User(10L));
        when(mapper.toJpaEntity(domain)).thenReturn(new CompanyJpaEntity());

        CompanyJpaEntity result = adapter.toEntity(domain);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals(10L, result.getUser().getId());
    }

    @Test
    @DisplayName("toDomain - Should handle null user")
    void toDomainShouldHandleNullUser() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        entity.setUser(null);
        when(mapper.toDomain(entity)).thenReturn(new Company(1L));

        Company result = adapter.toDomain(entity);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toDomain - Should handle user with null ID")
    void toDomainShouldHandleUserWithNullId() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        entity.setUser(new UserJpaEntity()); 
        when(mapper.toDomain(entity)).thenReturn(new Company(1L));

        Company result = adapter.toDomain(entity);

        assertNotNull(result);
        assertNull(result.getUser());
    }

    @Test
    @DisplayName("toDomain - Should map user with valid ID")
    void toDomainShouldMapUserWithId() {
        CompanyJpaEntity entity = new CompanyJpaEntity();
        UserJpaEntity userEntity = new UserJpaEntity();
        userEntity.setId(10L);
        entity.setUser(userEntity);
        when(mapper.toDomain(entity)).thenReturn(new Company(1L));

        Company result = adapter.toDomain(entity);

        assertNotNull(result);
        assertNotNull(result.getUser());
        assertEquals(10L, result.getUser().getId());
    }
}
