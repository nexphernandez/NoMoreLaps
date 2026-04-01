package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IReservationPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ReservationJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ReservationJpaRepository;
import com.nomorelaps.domain.models.Reservation;

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

    @Override
    protected ReservationJpaEntity toEntity(Reservation domain) {
        return mapper.toJpaEntity(domain);
    }

    @Override
    protected Reservation toDomain(ReservationJpaEntity entity) {
        return mapper.toDomain(entity);
    }

    @Override
    public List<Reservation> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Reservation> findByParkingSpotId(Long spotId) {
        return repository.findByParkingSpotId(spotId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Reservation> findByState(String state) {
        return repository.findByState(state).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
