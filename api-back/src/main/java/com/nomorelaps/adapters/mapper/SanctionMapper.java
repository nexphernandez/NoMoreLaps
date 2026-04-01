package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.SanctionRequest;
import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Sanction entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface SanctionMapper {
    SanctionJpaEntity toEntity(SanctionRequest request);
    SanctionResponse toResponse(SanctionJpaEntity entity);
}
