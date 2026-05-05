package com.nomorelaps.adapters.out.persistence;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.adapters.out.persistence.jpa.DynamicPriceJpaEntity;
import com.nomorelaps.adapters.out.persistence.repository.DynamicPriceJpaRepository;
import com.nomorelaps.domain.models.DynamicPrice;

@ExtendWith(MockitoExtension.class)
class DynamicPricePersistenceAdapterTest {

    @Mock
    private DynamicPriceJpaRepository repository;

    @Mock
    private DynamicPriceMapper mapper;

    @InjectMocks
    private DynamicPricePersistenceAdapter adapter;

    private DynamicPrice price;
    private DynamicPriceJpaEntity entity;

    @BeforeEach
    void setUp() {
        price = new DynamicPrice(1L);
        entity = new DynamicPriceJpaEntity();
        entity.setId(1L);
    }

    @Test
    @DisplayName("findByParkingId - Should return list")
    void shouldReturnByParkingId() {
        when(repository.findByParkingId(1L)).thenReturn(List.of(entity));
        when(mapper.toDomain(entity)).thenReturn(price);

        List<DynamicPrice> result = adapter.findByParkingId(1L);

        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("save - Should call toEntity and toDomain (covers protected toEntity)")
    void shouldSave() {
        when(mapper.toJpaEntity(price)).thenReturn(entity);
        when(repository.save(entity)).thenReturn(entity);
        when(mapper.toDomain(entity)).thenReturn(price);

        DynamicPrice saved = adapter.save(price);

        assertNotNull(saved);
        assertEquals(1L, saved.getId());
    }
}

