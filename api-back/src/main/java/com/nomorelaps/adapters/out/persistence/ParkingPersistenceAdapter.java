package com.nomorelaps.adapters.out.persistence;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nomorelaps.adapters.out.persistence.interfaces.IParkingPersistenceAdapter;
import com.nomorelaps.adapters.out.persistence.jpa.ParkingJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.ParkingJpaRepository;
import com.nomorelaps.adapters.out.persistence.repository.abstracta.BasePersistenceAdapter;

/**
 * Persistence implementation for ParkingJpaEntity via Spring Data repositories.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Component
public class ParkingPersistenceAdapter extends BasePersistenceAdapter<ParkingJpaEntity, 
        Long, ParkingJpaRepository> implements IParkingPersistenceAdapter{

    @Autowired
    public ParkingPersistenceAdapter(ParkingJpaRepository repository) {
        super(repository);
    }


    @Override
    public List<ParkingJpaEntity> findByCompanyId(Long companyId) {
        return repository.findByCompanyId(companyId);
    }
    
}
