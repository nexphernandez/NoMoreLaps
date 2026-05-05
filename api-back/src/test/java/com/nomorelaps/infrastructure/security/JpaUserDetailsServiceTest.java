package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;

@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    private UserJpaRepository userRepository;

    @InjectMocks
    private JpaUserDetailsService userDetailsService;

    @Test
    @DisplayName("loadUserByUsername - Should return SecurityUser when email exists")
    void shouldLoadUserByEmail() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("test@test.com");
        entity.setPassword("pass");
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(entity));

        UserDetails details = userDetailsService.loadUserByUsername("test@test.com");

        assertNotNull(details);
        assertEquals("test@test.com", details.getUsername());
    }

    @Test
    @DisplayName("loadUserByUsername - Should throw exception when user not found")
    void shouldThrowExceptionWhenNotFound() {
        when(userRepository.findByEmail("missing@test.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername("missing@test.com"));
    }
}
