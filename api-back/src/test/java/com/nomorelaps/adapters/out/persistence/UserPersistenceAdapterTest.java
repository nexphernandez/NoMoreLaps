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

import com.nomorelaps.adapters.mapper.UserMapper;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;
import com.nomorelaps.domain.models.User;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private UserJpaRepository repository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserPersistenceAdapter adapter;

    private User user;
    private UserJpaEntity entity;

    @BeforeEach
    void setUp() {
        user = new User(1L);
        entity = new UserJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByEmail - Should return user when found")
    void shouldReturnUserByEmail() {
        when(repository.findByEmail("test@test.com")).thenReturn(Optional.of(entity));
        when(userMapper.toDomain(entity)).thenReturn(user);

        Optional<User> result = adapter.findByEmail("test@test.com");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    @Test
    @DisplayName("existsByEmail - Should return boolean")
    void shouldCheckEmailExistence() {
        when(repository.existsByEmail("test@test.com")).thenReturn(true);
        assertTrue(adapter.existsByEmail("test@test.com"));
    }

    @Test
    @DisplayName("save - Should delegate to repository and mapper")
    void shouldSaveUser() {
        when(userMapper.toJpaEntity(user)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(userMapper.toDomain(entity)).thenReturn(user);

        User result = adapter.save(user);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }
}
