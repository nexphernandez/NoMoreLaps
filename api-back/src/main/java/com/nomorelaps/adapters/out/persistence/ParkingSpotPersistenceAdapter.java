package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingSpotPersistenceAdapter;
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

    @Override
    protected ParkingSpotJpaEntity toEntity(ParkingSpot domain) {
        return mapper.toJpaEntity(domain);
    }

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
