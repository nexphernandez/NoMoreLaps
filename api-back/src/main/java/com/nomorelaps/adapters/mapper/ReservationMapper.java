package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.ReservationRequest;
import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Reservation entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface ReservationMapper {
    ReservationJpaEntity toEntity(ReservationRequest request);
    ReservationResponse toResponse(ReservationJpaEntity entity);
}
