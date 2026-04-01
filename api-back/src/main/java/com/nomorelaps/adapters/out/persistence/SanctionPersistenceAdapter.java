package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.ISanctionPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.SanctionJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.SanctionJpaRepository;
import com.nomorelaps.domain.models.Sanction;

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

    @Override
    protected SanctionJpaEntity toEntity(Sanction domain) {
        return mapper.toJpaEntity(domain);
    }

    @Override
    protected Sanction toDomain(SanctionJpaEntity entity) {
        return mapper.toDomain(entity);
    }

    @Override
    public List<Sanction> findByUserId(Long userId) {
        return repository.findByUserId(userId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }

    @Override
    public List<Sanction> findByReservationId(Long reservationId) {
        return repository.findByReservationId(reservationId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
