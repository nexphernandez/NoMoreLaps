package com.nomorelaps.adapters.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import com.nomorelaps.adapters.in.api.NotificationResponse;
import com.nomorelaps.adapters.out.persistence.jpa.NotificationJpaEntity;
import com.nomorelaps.domain.models.Notification;

/**
 * Mapper for Notification.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NotificationMapper {

    /**
     * Converts a JPA entity to a domain model.
     * 
     * @param entity the JPA entity to convert
     * @return the converted domain model
     */
    Notification toDomain(NotificationJpaEntity entity);

    /**
     * Converts a domain model to a JPA entity.
     * 
     * @param domain the domain model to convert
     * @return the converted JPA entity
     */
    NotificationJpaEntity toJpaEntity(Notification domain);

    /**
     * Converts a domain model to a response DTO.
     * 
     * @param domain the domain model to convert
     * @return the converted response DTO
     */
    NotificationResponse toResponse(Notification domain);
}
