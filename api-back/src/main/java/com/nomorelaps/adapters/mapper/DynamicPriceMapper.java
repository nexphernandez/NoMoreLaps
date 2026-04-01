package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Dynamic Price entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface DynamicPriceMapper {
    DynamicPriceJpaEntity toEntity(DynamicPriceRequest request);
    DynamicPriceResponse toResponse(DynamicPriceJpaEntity entity);
}
