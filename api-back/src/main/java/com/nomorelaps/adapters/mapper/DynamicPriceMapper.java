package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.domain.models.DynamicPrice;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Dynamic Price entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface DynamicPriceMapper {
    DynamicPrice toDomainFromRequest(DynamicPriceRequest request);
    DynamicPriceResponse toResponse(DynamicPrice domain);
    DynamicPriceJpaEntity toJpaEntity(DynamicPrice domain);
    DynamicPrice toDomain(DynamicPriceJpaEntity entity);
}
