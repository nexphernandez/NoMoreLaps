package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Company entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface CompanyMapper {
    CompanyJpaEntity toEntity(CompanyRequest request);
    CompanyResponse toResponse(CompanyJpaEntity entity);
}
