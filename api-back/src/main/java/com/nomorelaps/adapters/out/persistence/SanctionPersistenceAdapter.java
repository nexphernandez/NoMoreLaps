package com.nomorelaps.adapters.out.persistence;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.ISanctionPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.SanctionJpaRepository;
import com.nomorelaps.domain.models.Sanction;

/**
 * Persistence implementation for Sanction via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class SanctionPersistenceAdapter 
        extends BasePersistenceAdapter<Sanction, SanctionJpaEntity, Long, SanctionJpaRepository> 
        implements ISanctionPersistenceAdapter {

    private final SanctionMapper mapper;

    @Autowired
    public SanctionPersistenceAdapter(SanctionJpaRepository repository, SanctionMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    /**
     * Converts a Sanction domain object to its JPA Entity equivalent.
     */
    @Override
    protected SanctionJpaEntity toEntity(Sanction domain) {
        SanctionJpaEntity entity = mapper.toJpaEntity(domain);
        if (domain.getUser() != null && domain.getUser().getId() != null) {
            com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity userEntity = new com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity();
            userEntity.setId(domain.getUser().getId());
            entity.setUser(userEntity);
        }
        if (domain.getReservation() != null && domain.getReservation().getId() != null) {
            com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity resEntity = new com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity();
            resEntity.setId(domain.getReservation().getId());
            entity.setReservation(resEntity);
        }
        return entity;
    }

    /**
     * Converts a Sanction JPA Entity to its domain model equivalent.
     */
    @Override
    protected Sanction toDomain(SanctionJpaEntity entity) {
        Sanction domain = mapper.toDomain(entity);
        if (entity.getUser() != null) {
            com.nomorelaps.domain.models.User user = new com.nomorelaps.domain.models.User();
            user.setId(entity.getUser().getId());
            domain.setUser(user);
        }
        if (entity.getReservation() != null) {
            com.nomorelaps.domain.models.Reservation res = new com.nomorelaps.domain.models.Reservation();
            res.setId(entity.getReservation().getId());
            domain.setReservation(res);
        }
        return domain;
    }

    @Override
    public List<Sanction> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Sanction> findByReservationId(Long reservationId) {
        return repository.findByReservationId(reservationId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
