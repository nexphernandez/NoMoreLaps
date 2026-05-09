package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.ISanctionPersistenceAdapter;
import com.nomorelaps.domain.models.Sanction;

@ExtendWith(MockitoExtension.class)
class SanctionServiceTest {

    @Mock
    private ISanctionPersistenceAdapter persistencePort;

    @InjectMocks
    private SanctionService sanctionService;

    @Test
    @DisplayName("create - Should delegate to persistence")
    void shouldCreateSanction() {
        Sanction s = new Sanction();
        when(persistencePort.save(s)).thenReturn(s);
        Sanction result = sanctionService.create(s);
        assertEquals(s, result);
        verify(persistencePort).save(s);
    }

    @Test
    @DisplayName("findById - Should return optional sanction")
    void shouldFindById() {
        Sanction s = new Sanction();
        when(persistencePort.findById(1L)).thenReturn(Optional.of(s));
        Optional<Sanction> result = sanctionService.findById(1L);
        assertTrue(result.isPresent());
        verify(persistencePort).findById(1L);
    }

    @Test
    @DisplayName("findByUserId - Should return list")
    void shouldFindByUserId() {
        when(persistencePort.findByUserId(1L)).thenReturn(Arrays.asList(new Sanction()));
        List<Sanction> result = sanctionService.findByUserId(1L);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("findByReservationId - Should return list")
    void shouldFindByReservationId() {
        when(persistencePort.findByReservationId(1L)).thenReturn(Arrays.asList(new Sanction()));
        List<Sanction> result = sanctionService.findByReservationId(1L);
        assertEquals(1, result.size());
    }

    @Test
    @DisplayName("update - Should delegate to persistence")
    void shouldUpdateSanction() {
        Sanction s = new Sanction();
        when(persistencePort.save(s)).thenReturn(s);
        Sanction result = sanctionService.update(s);
        assertEquals(s, result);
    }

    @Test
    @DisplayName("deleteById - Should call persistence")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        sanctionService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("findByCompanyId - Should return list of sanctions")
    void shouldFindByCompanyId() {
        Sanction s1 = new Sanction();
        Sanction s2 = new Sanction();
        when(persistencePort.findByCompanyId(1L)).thenReturn(Arrays.asList(s1, s2));

        List<Sanction> result = sanctionService.findByCompanyId(1L);

        assertEquals(2, result.size());
        verify(persistencePort).findByCompanyId(1L);
    }

    @Test
    @DisplayName("paySanction - Should set paid to true and save")
    void shouldPaySanction() {
        Sanction s = new Sanction();
        s.setId(1L);
        s.setPaid(false);

        when(persistencePort.findById(1L)).thenReturn(Optional.of(s));
        when(persistencePort.save(any(Sanction.class))).thenAnswer(i -> i.getArguments()[0]);

        Sanction result = sanctionService.paySanction(1L);

        assertTrue(result.isPaid());
        verify(persistencePort).save(s);
    }

    @Test
    @DisplayName("paySanction - Should throw when not found")
    void shouldThrowWhenSanctionNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sanctionService.paySanction(99L));
    }
}
