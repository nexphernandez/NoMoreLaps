package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ParkingRequest;
import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Parking entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ParkingMapper {
    ParkingJpaEntity toEntity(ParkingRequest request);
    ParkingResponse toResponse(ParkingJpaEntity entity);
}
