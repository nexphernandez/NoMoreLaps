package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.domain.models.Parking;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Parking entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ParkingMapper {
    /**
     * Converts an API request into a domain model.
     * 
     * @param request The incoming request DTO.
     * @return The domain model representation.
     */
    Parking toDomainFromRequest(ParkingRequest request);

    /**
     * Converts a domain model into an API response.
     * 
     * @param domain The business domain object.
     * @return The response DTO for the API.
     */
    ParkingResponse toResponse(Parking domain);

    /**
     * Converts a domain model into a JPA persistence entity.
     * 
     * @param domain The business domain object.
     * @return The JPA entity for database storage.
     */
    @Mapping(target = "parkingSpots", ignore = true)
    @Mapping(target = "dynamicPrice", ignore = true)
    @Mapping(target = "company", ignore = true)
    ParkingJpaEntity toJpaEntity(Parking domain);

    /**
     * Converts a JPA entity into a domain model.
     * 
     * @param entity The JPA entity from the database.
     * @return The business domain object.
     */
    @Mapping(target = "parkingSpots", ignore = true)
    @Mapping(target = "dynamicPrice", ignore = true)
    @Mapping(target = "company", ignore = true)
    Parking toDomain(ParkingJpaEntity entity);
}
