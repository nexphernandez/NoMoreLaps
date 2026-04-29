package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.domain.models.Reservation;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Reservation entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ReservationMapper {
    /**
     * Converts an API request into a domain model.
     * 
     * @param request The incoming request DTO.
     * @return The domain model representation.
     */
    @Mapping(target = "user.id", source = "userId")
    @Mapping(target = "parkingSpot.id", source = "parkingSpotId")
    Reservation toDomainFromRequest(ReservationRequest request);

    /**
     * Converts a domain model into an API response.
     * 
     * @param domain The business domain object.
     * @return The response DTO for the API.
     */
    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "parkingSpotId", source = "parkingSpot.id")
    @Mapping(target = "parkingName", source = "parkingSpot.parking.name")
    ReservationResponse toResponse(Reservation domain);

    /**
     * Converts a domain model into a JPA persistence entity.
     * 
     * @param domain The business domain object.
     * @return The JPA entity for database storage.
     */
    @Mapping(target = "sanctions", ignore = true)
    @Mapping(target = "parkingSpot", ignore = true)
    @Mapping(target = "user", ignore = true)
    ReservationJpaEntity toJpaEntity(Reservation domain);
    
    /**
     * Converts a JPA entity into a domain model.
     * 
     * @param entity The JPA entity from the database.
     * @return The business domain object.
     */
    @Mapping(target = "sanctions", ignore = true)
    @Mapping(target = "parkingSpot", ignore = true)
    @Mapping(target = "user", ignore = true)
    Reservation toDomain(ReservationJpaEntity entity);
}
