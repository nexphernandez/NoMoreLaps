package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
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
    Role toDomainFromRequest(RoleRequest request);
    RoleResponse toResponse(Role domain);
    RoleJpaEntity toJpaEntity(Role domain);
    Role toDomain(RoleJpaEntity entity);
}
