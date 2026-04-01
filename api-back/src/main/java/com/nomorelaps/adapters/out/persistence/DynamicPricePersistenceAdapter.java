package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.adapters.out.persistence.abstracta.BasePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.interfaces.IDynamicPricePersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.DynamicPriceJpaRepository;
import com.nomorelaps.domain.models.DynamicPrice;

/**
 * Persistence implementation for DynamicPrice via Spring Data repositories.
 * Uses the Mapper internally to isolate the JpaEntity.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class DynamicPricePersistenceAdapter 
        extends BasePersistenceAdapter<DynamicPrice, DynamicPriceJpaEntity, Long, DynamicPriceJpaRepository> 
        implements IDynamicPricePersistenceAdapter {

    private final DynamicPriceMapper mapper;

    @Autowired
    public DynamicPricePersistenceAdapter(DynamicPriceJpaRepository repository, DynamicPriceMapper mapper) {
        super(repository);
        this.mapper = mapper;
    }

    @Override
    protected DynamicPriceJpaEntity toEntity(DynamicPrice domain) {
        return mapper.toJpaEntity(domain);
    }

    @Override
    protected DynamicPrice toDomain(DynamicPriceJpaEntity entity) {
        return mapper.toDomain(entity);
    }

    @Override
    public List<DynamicPrice> findByParkingId(Long parkingId) {
        return repository.findByParkingId(parkingId).stream()
                .map(this::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
