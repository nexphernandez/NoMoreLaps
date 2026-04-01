package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DynamicPriceMapper {
    DynamicPriceJpaEntity toEntity(DynamicPriceRequest request);
    DynamicPriceResponse toResponse(DynamicPriceJpaEntity entity);
}
