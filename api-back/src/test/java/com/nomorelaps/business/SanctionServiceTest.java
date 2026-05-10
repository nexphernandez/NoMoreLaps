package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.adapters.out.persistence.interfaces.ISanctionPersistenceAdapter;
import com.nomorelaps.domain.models.Sanction;

/**
 * Unit tests for SanctionService.
 * Verifies business logic for sanction management and payment processing.
 */
@ExtendWith(MockitoExtension.class)
class SanctionServiceTest {

    @Mock
    private ISanctionPersistenceAdapter persistencePort;

    @InjectMocks
    private SanctionService sanctionService;

    private Sanction testSanction;
    private Sanction testSanctionEmphy;

    @BeforeEach
    void setUp() {
        testSanction = new Sanction();
        testSanctionEmphy = new Sanction();
        testSanction.setId(1L);
        testSanction.setPaid(false);
    }

    @Test
    @DisplayName("create - Should delegate to persistence port")
    void shouldCreateSanction() {
        when(persistencePort.save(any(Sanction.class))).thenReturn(testSanction);
        Sanction result = sanctionService.create(testSanction);
        assertNotNull(result);
        verify(persistencePort).save(testSanction);
    }

    @Test
    @DisplayName("paySanction - Should mark as paid and save")
    void shouldPaySanction() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testSanction));
        when(persistencePort.save(any(Sanction.class))).thenAnswer(i -> i.getArgument(0));

        Sanction result = sanctionService.paySanction(1L);

        assertTrue(result.isPaid());
        verify(persistencePort).save(testSanction);
    }

    @Test
    @DisplayName("paySanction - Should throw if not found")
    void shouldThrowIfNotFoundForPayment() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sanctionService.paySanction(99L));
    }

    @Test
    @DisplayName("Delegations - Should correctly call persistence ports for searches")
    void shouldDelegateSearches() {
        when(persistencePort.findByUserId(1L)).thenReturn(List.of());
        sanctionService.findByUserId(1L);
        verify(persistencePort).findByUserId(1L);

        when(persistencePort.findByReservationId(1L)).thenReturn(List.of());
        sanctionService.findByReservationId(1L);
        verify(persistencePort).findByReservationId(1L);

        when(persistencePort.findByCompanyId(1L)).thenReturn(List.of());
        sanctionService.findByCompanyId(1L);
        verify(persistencePort).findByCompanyId(1L);
    }

    @Test
    @DisplayName("update - Should delegate to persistence")
    void shouldUpdateSanction() {

        when(persistencePort.save(testSanctionEmphy)).thenReturn(testSanctionEmphy);
        Sanction result = sanctionService.update(testSanctionEmphy);
        assertEquals(testSanctionEmphy, result);
    }

    @Test
    @DisplayName("deleteById - Should call persistence")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        sanctionService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("paySanction - Should throw when not found")
    void shouldThrowWhenSanctionNotFound() {
        when(persistencePort.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sanctionService.paySanction(99L));
    }
    @Test
    @DisplayName("findById - Should return optional sanction")
    void shouldFindById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testSanctionEmphy));
        Optional<Sanction> result = sanctionService.findById(1L);
        assertTrue(result.isPresent());
        verify(persistencePort).findById(1L);
    }
}
