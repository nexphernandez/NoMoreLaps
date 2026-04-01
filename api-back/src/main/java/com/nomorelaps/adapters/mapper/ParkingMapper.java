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
    Parking toDomainFromRequest(ParkingRequest request);
    ParkingResponse toResponse(Parking domain);

    @Mapping(target = "parkingSpots", ignore = true)
    @Mapping(target = "dynamicPrice", ignore = true)
    @Mapping(target = "company", ignore = true)
    ParkingJpaEntity toJpaEntity(Parking domain);

    @Mapping(target = "parkingSpots", ignore = true)
    @Mapping(target = "dynamicPrice", ignore = true)
    @Mapping(target = "company", ignore = true)
    Parking toDomain(ParkingJpaEntity entity);
}
