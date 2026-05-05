package com.nomorelaps.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
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

@ExtendWith(MockitoExtension.class)
class SanctionServiceTest {

    @Mock
    private ISanctionPersistenceAdapter persistencePort;

    @InjectMocks
    private SanctionService sanctionService;

    private Sanction testSanction;

    @BeforeEach
    void setUp() {
        testSanction = new Sanction(1L);
        testSanction.setAmount(50.0);
        testSanction.setPaid(false);
    }

    @Test
    @DisplayName("Should create sanction")
    void shouldCreateSanction() {
        when(persistencePort.save(any(Sanction.class))).thenReturn(testSanction);
        Sanction created = sanctionService.create(testSanction);
        assertNotNull(created);
        verify(persistencePort).save(testSanction);
    }

    @Test
    @DisplayName("Should find sanction by id")
    void shouldFindById() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testSanction));
        Optional<Sanction> found = sanctionService.findById(1L);
        assertTrue(found.isPresent());
        assertEquals(testSanction, found.get());
    }

    @Test
    @DisplayName("Should find sanctions by user id")
    void shouldFindByUserId() {
        when(persistencePort.findByUserId(1L)).thenReturn(List.of(testSanction));
        List<Sanction> found = sanctionService.findByUserId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should find sanctions by reservation id")
    void shouldFindByReservationId() {
        when(persistencePort.findByReservationId(1L)).thenReturn(List.of(testSanction));
        List<Sanction> found = sanctionService.findByReservationId(1L);
        assertEquals(1, found.size());
    }

    @Test
    @DisplayName("Should update sanction")
    void shouldUpdateSanction() {
        when(persistencePort.save(any(Sanction.class))).thenReturn(testSanction);
        Sanction updated = sanctionService.update(testSanction);
        assertNotNull(updated);
        verify(persistencePort).save(testSanction);
    }

    @Test
    @DisplayName("Should delete sanction by id")
    void shouldDeleteById() {
        doNothing().when(persistencePort).deleteById(1L);
        sanctionService.deleteById(1L);
        verify(persistencePort).deleteById(1L);
    }

    @Test
    @DisplayName("Should mark sanction as paid")
    void shouldPaySanction() {
        when(persistencePort.findById(1L)).thenReturn(Optional.of(testSanction));
        when(persistencePort.save(any(Sanction.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Sanction paid = sanctionService.paySanction(1L);
        assertTrue(paid.isPaid());
        verify(persistencePort).save(testSanction);
    }

    @Test
    @DisplayName("Should throw exception when paying non-existent sanction")
    void shouldThrowExceptionWhenNotFound() {
        when(persistencePort.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> sanctionService.paySanction(999L));
    }
}
