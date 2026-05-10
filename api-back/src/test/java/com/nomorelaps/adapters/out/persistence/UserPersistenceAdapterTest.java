package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for UserPersistenceAdapter.
 * Verifies persistence logic for User, including mapping and repository interaction.
 */
@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private UserJpaRepository repository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserPersistenceAdapter adapter;

    private User testUser;
    private UserJpaEntity testEntity;

    @BeforeEach
    void setUp() {
        testUser = new User(1L);
        testUser.setEmail("test@test.com");
        testEntity = new UserJpaEntity();
        testEntity.setId(1L);
        testEntity.setEmail("test@test.com");
    }

    @Test
    @DisplayName("save - Should map to entity, save and return domain")
    void shouldSaveUser() {
        when(userMapper.toJpaEntity(testUser)).thenReturn(testEntity);
        when(repository.save(testEntity)).thenReturn(testEntity);
        when(userMapper.toDomain(testEntity)).thenReturn(testUser);

        User result = adapter.save(testUser);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repository).save(testEntity);
    }

    @Test
    @DisplayName("findByEmail - Should return domain object when email exists")
    void shouldReturnUserWhenEmailExists() {
        String email = "test@test.com";
        when(repository.findByEmail(email)).thenReturn(Optional.of(testEntity));
        when(userMapper.toDomain(testEntity)).thenReturn(testUser);

        Optional<User> result = adapter.findByEmail(email);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("findByEmail - Should return empty when email does not exist")
    void shouldReturnEmptyWhenEmailDoesNotExist() {
        String email = "notfound@test.com";
        when(repository.findByEmail(email)).thenReturn(Optional.empty());

        Optional<User> result = adapter.findByEmail(email);

        assertTrue(result.isEmpty());
    }

    @Test
    @DisplayName("existsByEmail - Should return true when email exists")
    void shouldReturnTrueWhenEmailExists() {
        String email = "exists@test.com";
        when(repository.existsByEmail(email)).thenReturn(true);

        assertTrue(adapter.existsByEmail(email));
    }

    @Test
    @DisplayName("existsByEmail - Should return false when email does not exist")
    void shouldReturnFalseWhenEmailDoesNotExist() {
        String email = "new@test.com";
        when(repository.existsByEmail(email)).thenReturn(false);

        assertFalse(adapter.existsByEmail(email));
    }

    @Test
    @DisplayName("findById - Should return domain object when ID exists")
    void shouldReturnUserWhenIdExists() {
        when(repository.findById(1L)).thenReturn(Optional.of(testEntity));
        when(userMapper.toDomain(testEntity)).thenReturn(testUser);

        Optional<User> result = adapter.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("deleteById - Should call repository delete")
    void shouldDeleteUserById() {
        Long id = 10L;
        adapter.deleteById(id);
        verify(repository).deleteById(id);
    }
}
