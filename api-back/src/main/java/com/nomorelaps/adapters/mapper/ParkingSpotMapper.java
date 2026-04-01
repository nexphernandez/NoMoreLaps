package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.domain.models.ParkingSpot;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Parking Spot entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ParkingSpotMapper {
    ParkingSpot toDomainFromRequest(ParkingSpotRequest request);
    ParkingSpotResponse toResponse(ParkingSpot domain);
    ParkingSpotJpaEntity toJpaEntity(ParkingSpot domain);
    ParkingSpot toDomain(ParkingSpotJpaEntity entity);
}
