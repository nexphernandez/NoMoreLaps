package com.nomorelaps.infrastructure.security;

import static org.junit.jupiter.api.Assertions.*;

import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SecurityUserTest {

    @Test
    @DisplayName("Should test all getters and constructors of SecurityUser")
    void testSecurityUser() {
        UserJpaEntity entity = new UserJpaEntity();
        entity.setEmail("test@test.com");
        entity.setPassword("pass");
        RoleJpaEntity role = new RoleJpaEntity();
        role.setName("USER");
        entity.setRole(role);

        SecurityUser su = new SecurityUser(entity, 123L);

        assertEquals("test@test.com", su.getUsername());
        assertEquals("pass", su.getPassword());
        assertEquals(123L, su.getCompanyId());
        assertEquals(entity, su.getUserEntity());
        assertTrue(su.isAccountNonExpired());
        assertTrue(su.isAccountNonLocked());
        assertTrue(su.isCredentialsNonExpired());
        assertTrue(su.isEnabled());
        assertFalse(su.getAuthorities().isEmpty());
    }

    @Test
    @DisplayName("Should return null companyId for normal constructor")
    void testNormalConstructor() {
        UserJpaEntity entity = new UserJpaEntity();
        SecurityUser su = new SecurityUser(entity);
        assertNull(su.getCompanyId());
    }
}
