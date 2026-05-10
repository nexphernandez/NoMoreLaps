package com.nomorelaps.infrastructure.security;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.UserJpaRepository;

/**
 * Unit tests for JpaUserDetailsService.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies the retrieval of user data from the database for authentication purposes.
 */
@ExtendWith(MockitoExtension.class)
class JpaUserDetailsServiceTest {

    @Mock
    private UserJpaRepository userJpaRepository;

    @InjectMocks
    private JpaUserDetailsService jpaUserDetailsService;

    private UserJpaEntity testUserEntity;
    private final String TEST_EMAIL = "found@test.com";

    @BeforeEach
    void setUp() {
        testUserEntity = new UserJpaEntity();
        testUserEntity.setEmail(TEST_EMAIL);
    }

    @Test
    @DisplayName("loadUserByUsername - Found: Should return a SecurityUser instance")
    void loadUserByUsername_Found_ShouldReturnSecurityUser() {
        when(userJpaRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUserEntity));

        UserDetails foundUserDetails = jpaUserDetailsService.loadUserByUsername(TEST_EMAIL);

        assertTrue(foundUserDetails instanceof SecurityUser);
    }

    @Test
    @DisplayName("loadUserByUsername - Found: Should map username correctly")
    void loadUserByUsername_Found_ShouldMapCorrectEmail() {
        when(userJpaRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(testUserEntity));

        UserDetails foundUserDetails = jpaUserDetailsService.loadUserByUsername(TEST_EMAIL);

        assertEquals(TEST_EMAIL, foundUserDetails.getUsername());
    }

    @Test
    @DisplayName("loadUserByUsername - Missing: Should throw UsernameNotFoundException")
    void loadUserByUsername_Missing_ShouldThrowException() {
        String missingEmail = "missing@test.com";
        when(userJpaRepository.findByEmail(missingEmail)).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> 
            jpaUserDetailsService.loadUserByUsername(missingEmail)
        );
    }

    @Test
    @DisplayName("loadUserByUsername - Missing: Should include email in error message")
    void loadUserByUsername_Missing_ShouldIncludeEmailInMessage() {
        String missingEmail = "missing@test.com";
        when(userJpaRepository.findByEmail(missingEmail)).thenReturn(Optional.empty());

        UsernameNotFoundException usernameNotFoundException = assertThrows(UsernameNotFoundException.class, () -> 
            jpaUserDetailsService.loadUserByUsername(missingEmail)
        );

        assertTrue(usernameNotFoundException.getMessage().contains(missingEmail));
    }
}
