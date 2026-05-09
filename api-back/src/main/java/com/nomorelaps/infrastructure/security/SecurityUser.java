package com.nomorelaps.infrastructure.security;

import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * Details adapter for Spring Security using the UserJpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class SecurityUser implements UserDetails {

    private final UserJpaEntity user;
    private final Long companyId;

    /**
     * Default constructor for normal users.
     * @param user The JPA entity of the user.
     */
    public SecurityUser(UserJpaEntity user) {
        this.user = user;
        this.companyId = null;
    }

    /**
     * Constructor for company-related users.
     * @param user The JPA entity of the user.
     * @param companyId The ID of the company this user belongs to.
     */
    public SecurityUser(UserJpaEntity user, Long companyId) {
        this.user = user;
        this.companyId = companyId;
    }

    /**
     * Gets the associated company ID.
     * @return The company ID or null if not a company user.
     */
    public Long getCompanyId() {
        return companyId;
    }

    /**
     * Gets the underlying JPA user entity.
     * @return The UserJpaEntity.
     */
    public UserJpaEntity getUserEntity() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (user.getRole() == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
