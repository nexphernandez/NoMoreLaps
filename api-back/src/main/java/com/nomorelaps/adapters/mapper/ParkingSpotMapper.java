package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ParkingSpotRequest;
import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Parking Spot entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ParkingSpotMapper {
    ParkingSpotJpaEntity toEntity(ParkingSpotRequest request);
    ParkingSpotResponse toResponse(ParkingSpotJpaEntity entity);
}
