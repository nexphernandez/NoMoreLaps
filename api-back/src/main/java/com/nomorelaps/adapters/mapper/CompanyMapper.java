package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
import com.nomorelaps.domain.models.Company;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for Company entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface CompanyMapper {
    Company toDomainFromRequest(CompanyRequest request);
    CompanyResponse toResponse(Company domain);

    @Mapping(target = "parkings", ignore = true)
    @Mapping(target = "user", ignore = true)
    CompanyJpaEntity toJpaEntity(Company domain);

    @Mapping(target = "parkings", ignore = true)
    @Mapping(target = "user", ignore = true)
    Company toDomain(CompanyJpaEntity entity);
}
