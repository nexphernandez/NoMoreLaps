package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;

/**
 * Unit tests for SecurityUser.
 * Adheres to the New Backend Test Refactoring Plan for granularity and business naming.
 * Verifies the mapping of JPA entities to Spring Security UserDetails.
 */
class SecurityUserTest {


    @Test
    @DisplayName("Constructor - User entity: Should initialize without companyId")
    void constructor_UserOnly_ShouldSetCompanyIdToNull() {
        UserJpaEntity userEntity = new UserJpaEntity();
        SecurityUser securityUser = new SecurityUser(userEntity);
        assertNull(securityUser.getCompanyId());
    }

    @Test
    @DisplayName("Constructor - Full: Should preserve provided companyId")
    void constructor_Full_ShouldPreserveCompanyId() {
        UserJpaEntity userEntity = new UserJpaEntity();
        SecurityUser securityUser = new SecurityUser(userEntity, 123L);
        assertEquals(123L, securityUser.getCompanyId());
    }


    @Test
    @DisplayName("getAuthorities - Role present: Should return prefixed role authority")
    void getAuthorities_RoleExists_ShouldReturnRoleWithPrefix() {
        RoleJpaEntity role = new RoleJpaEntity();
        role.setName("MANAGER");
        UserJpaEntity user = new UserJpaEntity();
        user.setRole(role);
        
        SecurityUser securityUser = new SecurityUser(user);
        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();

        assertEquals(1, authorities.size());
        assertEquals("ROLE_MANAGER", authorities.iterator().next().getAuthority());
    }

    @Test
    @DisplayName("getAuthorities - Role null: Should return empty collection")
    void getAuthorities_RoleNull_ShouldReturnEmptyList() {
        UserJpaEntity user = new UserJpaEntity();
        user.setRole(null);
        
        SecurityUser securityUser = new SecurityUser(user);
        assertTrue(securityUser.getAuthorities().isEmpty());
    }

    @Test
    @DisplayName("getUsername - Email mapping: Should return user email")
    void getUsername_ShouldReturnUserEmail() {
        UserJpaEntity user = new UserJpaEntity();
        user.setEmail("alice@test.com");
        SecurityUser securityUser = new SecurityUser(user);
        assertEquals("alice@test.com", securityUser.getUsername());
    }

    @Test
    @DisplayName("getPassword - Credential mapping: Should return user password")
    void getPassword_ShouldReturnUserPassword() {
        UserJpaEntity user = new UserJpaEntity();
        user.setPassword("encoded-hash");
        SecurityUser securityUser = new SecurityUser(user);
        assertEquals("encoded-hash", securityUser.getPassword());
    }


    @Test
    @DisplayName("isAccountNonExpired - Default: Should return true")
    void isAccountNonExpired_ShouldReturnTrue() {
        SecurityUser securityUser = new SecurityUser(new UserJpaEntity());
        assertTrue(securityUser.isAccountNonExpired());
    }

    @Test
    @DisplayName("isAccountNonLocked - Default: Should return true")
    void isAccountNonLocked_ShouldReturnTrue() {
        SecurityUser securityUser = new SecurityUser(new UserJpaEntity());
        assertTrue(securityUser.isAccountNonLocked());
    }

    @Test
    @DisplayName("isCredentialsNonExpired - Default: Should return true")
    void isCredentialsNonExpired_ShouldReturnTrue() {
        SecurityUser securityUser = new SecurityUser(new UserJpaEntity());
        assertTrue(securityUser.isCredentialsNonExpired());
    }

    @Test
    @DisplayName("isEnabled - Default: Should return true")
    void isEnabled_ShouldReturnTrue() {
        SecurityUser securityUser = new SecurityUser(new UserJpaEntity());
        assertTrue(securityUser.isEnabled());
    }
}
