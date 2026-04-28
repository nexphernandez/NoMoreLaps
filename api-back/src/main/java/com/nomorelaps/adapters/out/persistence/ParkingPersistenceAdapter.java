package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ParkingJpaRepository;
import com.nomorelaps.domain.models.Parking;

/**
 * Persistence implementation for Parking via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class ParkingPersistenceAdapter
        extends BasePersistenceAdapter<Parking, ParkingJpaEntity, Long, ParkingJpaRepository>
        implements IParkingPersistenceAdapter {

    private final ParkingMapper mapper;

    @Autowired
    public ParkingPersistenceAdapter(ParkingJpaRepository repository, ParkingMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    protected ParkingJpaEntity toEntity(Parking domain) {
        return mapper.toJpaEntity(domain);
    }

    @Override
    protected Parking toDomain(ParkingJpaEntity entity) {
        return mapper.toDomain(entity);
    }

    @Override
    public List<Parking> findByCompanyId(Long companyId) {
        return repository.findByCompanyId(companyId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Parking> searchByNameOrAddress(String query) {
        return repository.findByNameContainingIgnoreCaseOrAddressContainingIgnoreCase(query, query).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Parking> findNearby(double lat, double lng, double radiusInKm) {
        double margin = radiusInKm / 111.0;
        
        double minLat = lat - margin;
        double maxLat = lat + margin;
        double minLng = lng - margin;
        double maxLng = lng + margin;

        return repository.findByLatitudeBetweenAndLongitudeBetween(minLat, maxLat, minLng, maxLng).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
