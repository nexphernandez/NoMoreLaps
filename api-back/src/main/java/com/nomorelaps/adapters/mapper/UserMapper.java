package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.domain.models.User;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for User entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface UserMapper {
    User toDomainFromRequest(UserRequest request);
    UserResponse toResponse(User domain);

    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "sanctions", ignore = true)
    UserJpaEntity toJpaEntity(User domain);

    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "sanctions", ignore = true)
    User toDomain(UserJpaEntity entity);
}
