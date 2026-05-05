package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.nomorelaps.adapters.out.persistence.interfaces.IUserPersistenceAdapter;
import com.nomorelaps.domain.models.User;

/**
 * Unit tests for UserService covering all business methods.
 *
 * @author nexphernandez
 * @version 1.0.0
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private IUserPersistenceAdapter persistencePort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1L);
        testUser.setName("Alice");
        testUser.setEmail("alice@test.com");
        testUser.setPassword("plainPassword");
    }

    // ── create ──────────────────────────────────────────────────────────────

    @Test
    @DisplayName("create - Should encode password and save user successfully")
    void shouldCreateUserSuccessfully() {
        when(persistencePort.findByEmail("alice@test.com")).thenReturn(Optional.empty());
        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(persistencePort.save(any(User.class))).thenReturn(testUser);

        User result = userService.create(testUser);

        assertNotNull(result);
        verify(passwordEncoder).encode("plainPassword");
        verify(persistencePort).save(testUser);
    }

    @Test
    @DisplayName("create - Should throw if email already registered")
    void shouldThrowWhenEmailAlreadyExists() {
        when(persistencePort.findByEmail("alice@test.com")).thenReturn(Optional.of(testUser));

        assertThrows(IllegalArgumentException.class, () -> userService.create(testUser));
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("create - Should throw if password is null")
    void shouldThrowWhenPasswordIsNull() {
        testUser.setPassword(null);
        when(persistencePort.findByEmail("alice@test.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.create(testUser));
        verify(persistencePort, never()).save(any());
    }

    @Test
    @DisplayName("create - Should throw if password is empty")
    void shouldThrowWhenPasswordIsEmpty() {
        testUser.setPassword("");
        when(persistencePort.findByEmail("alice@test.com")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.create(testUser));
        verify(persistencePort, never()).save(any());
    }

    // ── findById ─────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findById - Should return user when found")
    void shouldFindUserById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("Alice", result.get().getName());
    }

    @Test
    @DisplayName("findById - Should return empty when not found")
    void shouldReturnEmptyWhenUserNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());

        Optional<User> result = userService.findById(99L);

        assertFalse(result.isPresent());
    }

    // ── findByEmail ───────────────────────────────────────────────────────────

    @Test
    @DisplayName("findByEmail - Should return user by email")
    void shouldFindUserByEmail() {
        when(persistencePort.findByEmail("alice@test.com")).thenReturn(Optional.of(testUser));

        Optional<User> result = userService.findByEmail("alice@test.com");

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
    }

    // ── findAll ───────────────────────────────────────────────────────────────

    @Test
    @DisplayName("findAll - Should return all users")
    void shouldReturnAllUsers() {
        User u2 = new User(2L);
        u2.setName("Bob");
        when(persistencePort.findAll()).thenReturn(Arrays.asList(testUser, u2));

        List<User> result = userService.findAll();

        assertEquals(2, result.size());
    }

    // ── update ────────────────────────────────────────────────────────────────

    @Test
    @DisplayName("update - Should update name and email when provided")
    void shouldUpdateUserNameAndEmail() {
        User existingUser = new User(1L);
        existingUser.setName("Old Name");
        existingUser.setEmail("old@test.com");

        User updates = new User(1L);
        updates.setName("New Name");
        updates.setEmail("new@test.com");
        updates.setPassword(null);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(persistencePort.save(any(User.class))).thenReturn(existingUser);

        User result = userService.update(updates);

        assertNotNull(result);
        assertEquals("New Name", existingUser.getName());
        assertEquals("new@test.com", existingUser.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("update - Should encode new password when provided")
    void shouldEncodePasswordOnUpdate() {
        User existingUser = new User(1L);
        existingUser.setName("Alice");
        existingUser.setPassword("old");

        User updates = new User(1L);
        updates.setPassword("newPassword");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode("newPassword")).thenReturn("newEncoded");
        when(persistencePort.save(any(User.class))).thenReturn(existingUser);

        userService.update(updates);

        verify(passwordEncoder).encode("newPassword");
        assertEquals("newEncoded", existingUser.getPassword());
    }

    @Test
    @DisplayName("update - Should throw when user not found")
    void shouldThrowWhenUpdatingNonExistentUser() {
        User updates = new User(99L);
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> userService.update(updates));
    }

    // ── deleteById ────────────────────────────────────────────────────────────

    @Test
    @DisplayName("deleteById - Should call persistence deleteById")
    void shouldDeleteUserById() {
        doNothing().when(persistencePort).deleteById(1L);

        userService.deleteById(1L);

        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("update - Should skip name/email update when null or empty")
    void shouldSkipNullFieldsOnUpdate() {
        User existingUser = new User(1L);
        existingUser.setName("Alice");
        existingUser.setEmail("alice@test.com");

        // Provide null name, empty email, null password → no fields should be updated
        User updates = new User(1L);
        updates.setName(null);
        updates.setEmail("");
        updates.setPassword(null);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(persistencePort.save(any(User.class))).thenReturn(existingUser);

        User result = userService.update(updates);

        assertNotNull(result);
        // original values preserved
        assertEquals("Alice", existingUser.getName());
        assertEquals("alice@test.com", existingUser.getEmail());
        verify(passwordEncoder, never()).encode(anyString());
    }

    @Test
    @DisplayName("update - Should update calendarEnable")
    void shouldUpdateCalendarEnable() {
        User existingUser = new User(1L);
        existingUser.setCalendarEnable(false);

        User updates = new User(1L);
        updates.setCalendarEnable(true);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(persistencePort.save(any(User.class))).thenReturn(existingUser);

        User result = userService.update(updates);

        assertTrue(result.isCalendarEnable());
        verify(persistencePort).save(existingUser);
    }

    @Test
    @DisplayName("update - Should skip name and password when they are empty strings")
    void shouldSkipEmptyStringsOnUpdate() {
        User existingUser = new User(1L);
        existingUser.setName("Alice");
        existingUser.setPassword("encoded");

        User updates = new User(1L);
        updates.setName("");
        updates.setPassword("");

        when(persistencePort.findById(1L)).thenReturn(Optional.of(existingUser));
        when(persistencePort.save(any(User.class))).thenReturn(existingUser);

        userService.update(updates);

        assertEquals("Alice", existingUser.getName());
        assertEquals("encoded", existingUser.getPassword());
        verify(passwordEncoder, never()).encode(anyString());
    }
}

