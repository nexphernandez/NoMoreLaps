package com.nomorelaps.infrastructure.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nomorelaps.domain.models.Company;

/**
 * Service to handle security checks and ownership validation.
 * Used for @PreAuthorize annotations in controllers.
 * 
 * @author nexphernandez
 * @version 1.0.0
 */
@Service("securityService")
public class SecurityService {

    /**
     * Checks if the currently authenticated company (via API Key) or user (via JWT)
     * matches the requested companyId.
     * 
     * @param companyId The ID of the company to check ownership against.
     * @return true if the requester is an ADMIN or the owner of the requested ID.
     */
    public boolean isCompanyOwner(Long companyId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null) return false;

        if (auth.getDetails() instanceof Company) {
            Company company = (Company) auth.getDetails();
            return company.getId().equals(companyId);
        }

        if (auth.getPrincipal() instanceof SecurityUser) {
            SecurityUser securityUser = (SecurityUser) auth.getPrincipal();
            
            if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
                return true;
            }
            
            if (securityUser.getCompanyId() != null) {
                return securityUser.getCompanyId().equals(companyId);
            }
        }

        return false; 
    }
}
