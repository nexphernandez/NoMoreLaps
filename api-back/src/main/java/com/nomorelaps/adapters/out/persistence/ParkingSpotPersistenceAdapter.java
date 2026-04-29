package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ParkingSpotJpaRepository;
import com.nomorelaps.domain.models.ParkingSpot;

/**
 * Persistence implementation for ParkingSpot via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class ParkingSpotPersistenceAdapter 
        extends BasePersistenceAdapter<ParkingSpot, ParkingSpotJpaEntity, Long, ParkingSpotJpaRepository> 
        implements IParkingSpotPersistenceAdapter {

    private final ParkingSpotMapper mapper;

    @Autowired
    public ParkingSpotPersistenceAdapter(ParkingSpotJpaRepository repository, ParkingSpotMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    /**
     * Converts a ParkingSpot domain object to its JPA Entity equivalent.
     * Special logic is applied to preserve the relationship with the Parking facility
     * during updates, avoiding orphans if the domain object lacks the parking reference.
     */
    @Override
    protected ParkingSpotJpaEntity toEntity(ParkingSpot domain) {
        ParkingSpotJpaEntity entity = mapper.toJpaEntity(domain);
        if (domain.getParking() != null && domain.getParking().getId() != null) {
            entity.setParking(new ParkingJpaEntity(domain.getParking().getId()));
        } else if (domain.getId() != null) {
            repository.findById(domain.getId()).ifPresent(existing -> {
                entity.setParking(existing.getParking());
            });
        }
        return entity;
    }

    /**
     * Converts a ParkingSpot JPA Entity to its domain model equivalent.
     */
    @Override
    protected ParkingSpot toDomain(ParkingSpotJpaEntity entity) {
        return mapper.toDomain(entity);
    }

    @Override
    public List<ParkingSpot> findByParkingId(Long parkingId) {
        return repository.findByParkingId(parkingId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<ParkingSpot> findByParkingIdAndStateTrue(Long parkingId) {
        return repository.findByParkingIdAndStateTrue(parkingId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
