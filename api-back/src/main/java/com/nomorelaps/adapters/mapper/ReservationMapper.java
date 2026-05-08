package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.Sanction;

/**
 * Mapper interface for Reservation entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Mapper(componentModel = "spring", uses = {SanctionMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
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
    @Mapping(target = "userName", source = "user.name")
    @Mapping(target = "parkingSpotId", source = "parkingSpot.id")
    @Mapping(target = "parkingName", source = "parkingSpot.parking.name")
    @Mapping(target = "sanctionPrice", expression = "java(calculateSanctionPrice(domain))")
    ReservationResponse toResponse(Reservation domain);

    default double calculateSanctionPrice(Reservation domain) {
        if (domain.getSanctions() == null) return 0.0;
        return domain.getSanctions().stream().mapToDouble(Sanction::getAmount).sum();
    }

    /**
     * Converts a domain model into a JPA persistence entity.
     * 
     * @param domain The business domain object.
     * @return The JPA entity for database storage.
     */
    @Mapping(target = "parkingSpot", ignore = true)
    @Mapping(target = "user", ignore = true)
    ReservationJpaEntity toJpaEntity(Reservation domain);
    
    /**
     * Converts a JPA entity into a domain model.
     * 
     * @param entity The JPA entity from the database.
     * @return The business domain object.
     */
    @Mapping(target = "parkingSpot", ignore = true)
    @Mapping(target = "user", ignore = true)
    Reservation toDomain(ReservationJpaEntity entity);
}
