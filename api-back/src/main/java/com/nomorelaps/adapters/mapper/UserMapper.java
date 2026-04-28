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
    /**
     * Converts an API request into a domain model.
     * 
     * @param request The incoming request DTO.
     * @return The domain model representation.
     */
    User toDomainFromRequest(UserRequest request);

    /**
     * Converts a domain model into an API response.
     * 
     * @param domain The business domain object.
     * @return The response DTO for the API.
     */
    UserResponse toResponse(User domain);

    /**
     * Converts a domain model into a JPA persistence entity.
     * 
     * @param domain The business domain object.
     * @return The JPA entity for database storage.
     */
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "sanctions", ignore = true)
    UserJpaEntity toJpaEntity(User domain);

    /**
     * Converts a JPA entity into a domain model.
     * 
     * @param entity The JPA entity from the database.
     * @return The business domain object.
     */
    @Mapping(target = "companies", ignore = true)
    @Mapping(target = "reservations", ignore = true)
    @Mapping(target = "sanctions", ignore = true)
    User toDomain(UserJpaEntity entity);
}
