package com.nomorelaps.infrastructure.data;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.nomorelaps.business.interfaces.*;
import com.nomorelaps.domain.models.*;

@ExtendWith(MockitoExtension.class)
class DataInitializerTest {

    @Mock
    private IRoleService roleService;
    @Mock
    private IUserService userService;
    @Mock
    private ICompanyService companyService;
    @Mock
    private IParkingService parkingService;
    @Mock
    private IParkingSpotService parkingSpotService;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        // Default behavior to avoid NPEs and facilitate tests
        Role r1 = new Role(1L);
        r1.setName("USER");
        Role r2 = new Role(2L);
        r2.setName("COMPANY");
        when(roleService.findAll()).thenReturn(List.of(r1, r2));
    }

    @Test
    @DisplayName("run - Should skip initialization if data already exists")
    void shouldSkipIfDataExists() {
        // Arrange
        when(userService.findByEmail("test@test.com")).thenReturn(Optional.of(new User()));
        when(userService.findByEmail("company@test.com")).thenReturn(Optional.of(new User()));
        when(companyService.findAll()).thenReturn(List.of(new Company()));
        when(parkingService.findAll()).thenReturn(List.of(new Parking()));

        // Act
        try {
            dataInitializer.run();
        } catch (Exception e) {
            fail("Should not throw exception");
        }

        // Assert
        verify(userService, never()).create(any());
        verify(companyService, never()).create(any());
        verify(parkingService, never()).create(any());
    }

    @Test
    @DisplayName("run - Should create data when repositories are empty")
    void shouldCreateDataWhenEmpty() throws Exception {
        // Arrange
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        
        // Mock role creation
        when(roleService.findAll()).thenReturn(Collections.emptyList());
        when(roleService.create(any())).thenAnswer(i -> i.getArgument(0));

        // Mock user creation
        User testUser = new User();
        testUser.setEmail("test@test.com");
        when(userService.create(any())).thenAnswer(i -> i.getArgument(0));
        
        // This makes testUser.isPresent() true for the company check
        // We need to change the behavior of findByEmail after the first calls
        when(userService.findByEmail("test@test.com"))
            .thenReturn(Optional.empty()) // First call in run()
            .thenReturn(Optional.of(testUser)); // Second call for company check

        when(companyService.findAll()).thenReturn(Collections.emptyList());
        when(companyService.create(any())).thenAnswer(i -> i.getArgument(0));

        when(parkingService.findAll()).thenReturn(Collections.emptyList());
        when(parkingService.create(any())).thenAnswer(i -> {
            Parking p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        // Act
        dataInitializer.run();

        // Assert
        verify(userService, times(2)).create(any());
        verify(companyService).create(any());
        verify(parkingService, times(3)).create(any());
        verify(parkingSpotService, times(15)).create(any());
    }

    @Test
    @DisplayName("run - Should not create company if test user is missing")
    void shouldNotCreateCompanyIfUserMissing() throws Exception {
        // Arrange
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyService.findAll()).thenReturn(Collections.emptyList());
        
        // Act
        dataInitializer.run();

        // Assert
        verify(companyService, never()).create(any());
    }

    @Test
    @DisplayName("run - Should not create parkings if company is null")
    void shouldNotCreateParkingsIfCompanyNull() throws Exception {
        // Arrange
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(companyService.findAll()).thenReturn(Collections.emptyList()); // Will try to find but fail to create company if we don't mock it?
        // Wait, if findAll is empty and testUser is missing, company remains null
        
        when(userService.findByEmail("test@test.com")).thenReturn(Optional.empty());
        
        when(parkingService.findAll()).thenReturn(Collections.emptyList());

        // Act
        dataInitializer.run();

        // Assert
        verify(parkingService, never()).create(any());
    }
}
