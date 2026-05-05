package com.nomorelaps.infrastructure.security;

import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

class SecurityUserTest {

    @Test
    void testSecurityUserWithRole() {
        UserJpaEntity user = new UserJpaEntity();
        user.setEmail("test@test.com");
        user.setPassword("pass");
        
        RoleJpaEntity role = new RoleJpaEntity();
        role.setName("USER");
        user.setRole(role);

        SecurityUser securityUser = new SecurityUser(user);

        assertEquals("test@test.com", securityUser.getUsername());
        assertEquals("pass", securityUser.getPassword());
        assertTrue(securityUser.isAccountNonExpired());
        assertTrue(securityUser.isAccountNonLocked());
        assertTrue(securityUser.isCredentialsNonExpired());
        assertTrue(securityUser.isEnabled());

        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
        assertNotNull(authorities);
        assertEquals(1, authorities.size());
        assertEquals("ROLE_USER", authorities.iterator().next().getAuthority());
    }

    @Test
    void testSecurityUserWithoutRole() {
        UserJpaEntity user = new UserJpaEntity();
        user.setEmail("admin@test.com");
        user.setPassword("adminpass");

        SecurityUser securityUser = new SecurityUser(user);

        Collection<? extends GrantedAuthority> authorities = securityUser.getAuthorities();
        assertNotNull(authorities);
        assertTrue(authorities.isEmpty());
    }
}
