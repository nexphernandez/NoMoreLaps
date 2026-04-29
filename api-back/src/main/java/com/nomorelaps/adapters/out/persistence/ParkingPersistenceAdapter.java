package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.CompanyJpaEntity;
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

    /**
     * Converts a Parking domain object to its JPA Entity.
     * Manually sets the Company reference to ensure the relationship is 
     * correctly persisted in the database.
     */
    @Override
    protected ParkingJpaEntity toEntity(Parking domain) {
        ParkingJpaEntity entity = mapper.toJpaEntity(domain);
        if (domain.getCompany() != null && domain.getCompany().getId() != null) {
            entity.setCompany(new CompanyJpaEntity(domain.getCompany().getId()));
        }
        return entity;
    }

    /**
     * Converts a Parking JPA Entity back to its domain model.
     */
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
