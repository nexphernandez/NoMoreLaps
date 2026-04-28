package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;
import com.nomorelaps.domain.models.Role;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Role entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface RoleMapper {

    /**
     * Converts an API request into a domain model.
     * 
     * @param request The incoming request DTO.
     * @return The domain model representation.
     */
    Role toDomainFromRequest(RoleRequest request);

    /**
     * Converts a domain model into an API response.
     * 
     * @param domain The business domain object.
     * @return The response DTO for the API.
     */
    RoleResponse toResponse(Role domain);

    /**
     * Converts a domain model into a JPA persistence entity.
     * 
     * @param domain The business domain object.
     * @return The JPA entity for database storage.
     */
    @Mapping(target = "users", ignore = true)
    RoleJpaEntity toJpaEntity(Role domain);
    
    /**
     * Converts a JPA entity into a domain model.
     * 
     * @param entity The JPA entity from the database.
     * @return The business domain object.
     */
    Role toDomain(RoleJpaEntity entity);
}
