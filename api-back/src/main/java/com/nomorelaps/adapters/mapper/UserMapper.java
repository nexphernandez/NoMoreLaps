package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.nomorelaps.adapters.in.api.UserRequest;
import com.nomorelaps.adapters.in.api.UserResponse;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
/**
 * Mapper interface for User entity and DTOs.
 * Uses MapStruct to convert between API Requests/Responses and JPA Entities.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public interface UserMapper {
    UserJpaEntity toEntity(UserRequest userRequest);
    UserResponse toResponse(UserJpaEntity userJpaEntity);
}
