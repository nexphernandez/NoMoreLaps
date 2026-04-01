package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.RoleRequest;
import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.out.persistence.jpa.RoleJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Role entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface RoleMapper {
    RoleJpaEntity toEntity(RoleRequest request);
    RoleResponse toResponse(RoleJpaEntity entity);
}
