package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.SanctionRequest;
import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.domain.models.Sanction;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Sanction entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface SanctionMapper {
    /**
     * Converts an API request into a domain model.
     * 
     * @param request The incoming request DTO.
     * @return The domain model representation.
     */
    Sanction toDomainFromRequest(SanctionRequest request);

    /**
     * Converts a domain model into an API response.
     * 
     * @param domain The business domain object.
     * @return The response DTO for the API.
     */
    SanctionResponse toResponse(Sanction domain);

    /**
     * Converts a domain model into a JPA persistence entity.
     * 
     * @param domain The business domain object.
     * @return The JPA entity for database storage.
     */
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "user", ignore = true)
    SanctionJpaEntity toJpaEntity(Sanction domain);

    /**
     * Converts a JPA entity into a domain model.
     * 
     * @param entity The JPA entity from the database.
     * @return The business domain object.
     */
    @Mapping(target = "reservation", ignore = true)
    @Mapping(target = "user", ignore = true)
    Sanction toDomain(SanctionJpaEntity entity);
}
