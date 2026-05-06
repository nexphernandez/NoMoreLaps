package com.nomorelaps.adapters.out.persistence;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingSpotJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.adapters.out.persistence.jpa.UserJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ReservationJpaRepository;
import com.nomorelaps.domain.models.Parking;
import com.nomorelaps.domain.models.ParkingSpot;
import com.nomorelaps.domain.models.Reservation;
import com.nomorelaps.domain.models.User;

/**
 * Persistence implementation for Reservation via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class ReservationPersistenceAdapter
        extends BasePersistenceAdapter<Reservation, ReservationJpaEntity, Long, ReservationJpaRepository>
        implements IReservationPersistenceAdapter {

    private final ReservationMapper mapper;

    @Autowired
    public ReservationPersistenceAdapter(ReservationJpaRepository repository, ReservationMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    /**
     * Converts a Reservation domain object to its JPA Entity.
     * Manually sets nested entities (User, ParkingSpot) to avoid circular
     * dependency issues during standard mapping.
     */
    @Override
    protected ReservationJpaEntity toEntity(Reservation domain) {
        ReservationJpaEntity entity = mapper.toJpaEntity(domain);

        if (domain.getUser() != null && domain.getUser().getId() != null) {
            UserJpaEntity userEntity = new UserJpaEntity();
            userEntity.setId(domain.getUser().getId());
            entity.setUser(userEntity);
        }

        if (domain.getParkingSpot() != null && domain.getParkingSpot().getId() != null) {
            ParkingSpotJpaEntity spotEntity = new ParkingSpotJpaEntity();
            spotEntity.setId(domain.getParkingSpot().getId());
            entity.setParkingSpot(spotEntity);
        }

        return entity;
    }

    /**
     * Converts a Reservation JPA Entity back to its domain model.
     * Manually reconstructs relationship links (User -> Spot -> Parking)
     * to ensure data availability in the frontend.
     */
    @Override
    protected Reservation toDomain(ReservationJpaEntity entity) {
        Reservation domain = mapper.toDomain(entity);

        if (entity.getUser() != null) {
            User user = new User();
            user.setId(entity.getUser().getId());
            user.setName(entity.getUser().getName());
            domain.setUser(user);
        }
        if (entity.getParkingSpot() != null) {
            ParkingSpot spot = new ParkingSpot();
            spot.setId(entity.getParkingSpot().getId());

            if (entity.getParkingSpot().getParking() != null) {
                ParkingJpaEntity pEntity = entity.getParkingSpot().getParking();
                Parking parking = new Parking();
                parking.setId(pEntity.getId());
                parking.setName(pEntity.getName());
                parking.setAddress(pEntity.getAddress());
                parking.setLatitude(pEntity.getLatitude());
                parking.setLongitude(pEntity.getLongitude());
                parking.setPricePerHour(pEntity.getPricePerHour());
                parking.setSanctionAmount(pEntity.getSanctionAmount());
                parking.setSanctionIntervalInMinutes(pEntity.getSanctionIntervalInMinutes());
                spot.setParking(parking);
            }

            domain.setParkingSpot(spot);
        }

        return domain;
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByParkingSpotId(Long spotId) {
        return repository.findByParkingSpotId(spotId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByParkingId(Long parkingId) {
        return repository.findByParkingSpotParkingId(parkingId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Reservation> findByState(String state) {
        return repository.findByState(state).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public boolean hasOverlappingReservations(Long spotId, LocalDateTime start, LocalDateTime end) {
        return !repository.findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfter(spotId, "ACTIVE", end, start)
                .isEmpty();
    }

    @Override
    public boolean hasOverlappingReservationsExcluding(Long spotId, LocalDateTime start, LocalDateTime end,
            Long excludeId) {
        return !repository
                .findByParkingSpotIdAndStateAndStartTimeBeforeAndEndTimeAfterAndIdNot(spotId, "ACTIVE", end, start,
                        excludeId)
                .isEmpty();
    }

    @Override
    public List<Reservation> findByCompanyId(Long companyId) {
        return repository.findByParkingSpotParkingCompanyId(companyId).stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
    }
}
