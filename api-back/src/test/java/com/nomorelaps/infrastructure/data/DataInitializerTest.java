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
import org.springframework.security.crypto.password.PasswordEncoder;

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
    @Mock
    private INotificationService notificationService;
    @Mock
    private IReservationService reservationService;
    @Mock
    private ISanctionService sanctionService;
    @Mock
    private IDynamicPriceService dynamicPriceService;
    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private DataInitializer dataInitializer;

    @BeforeEach
    void setUp() {
        Role r1 = new Role(1L);
        r1.setName("USER");
        Role r2 = new Role(2L);
        r2.setName("COMPANY");
        when(roleService.findAll()).thenReturn(List.of(r1, r2));
        
        lenient().when(notificationService.findByCompanyId(anyLong())).thenReturn(Collections.emptyList());
        lenient().when(reservationService.findByCompanyId(anyLong())).thenReturn(Collections.emptyList());
        lenient().when(parkingService.findAll()).thenReturn(Collections.emptyList());
    }

    @Test
    @DisplayName("run - Should skip initialization if data already exists")
    void shouldSkipIfDataExists() {
        when(userService.findByEmail("test@test.com")).thenReturn(Optional.of(new User()));
        when(userService.findByEmail("company@test.com")).thenReturn(Optional.of(new User()));
        when(companyService.findAll()).thenReturn(List.of(new Company()));
        reset(parkingService); 
        when(parkingService.findAll()).thenReturn(List.of(new Parking()));

        try {
            dataInitializer.run();
        } catch (Exception e) {
            fail("Should not throw exception");
        }

        verify(userService, never()).create(any());
        verify(companyService, never()).create(any());
        verify(parkingService, never()).create(any());
    }

    @Test
    @DisplayName("run - Should create data when repositories are empty")
    void shouldCreateDataWhenEmpty() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        
        when(roleService.findAll()).thenReturn(Collections.emptyList());
        when(roleService.create(any())).thenAnswer(i -> i.getArgument(0));

        User testUser = new User();
        testUser.setEmail("test@test.com");
        when(userService.create(any())).thenAnswer(i -> i.getArgument(0));
        

        when(userService.findByEmail("test@test.com"))
            .thenReturn(Optional.empty()) 
            .thenReturn(Optional.of(testUser)); 

        User compUser = new User();
        compUser.setEmail("company@test.com");
        when(userService.findByEmail("company@test.com"))
            .thenReturn(Optional.empty())
            .thenReturn(Optional.of(compUser));

        when(companyService.findAll()).thenReturn(Collections.emptyList());
        when(companyService.create(any())).thenAnswer(i -> i.getArgument(0));

        when(parkingService.findAll()).thenReturn(Collections.emptyList());
        when(parkingService.create(any())).thenAnswer(i -> {
            Parking p = i.getArgument(0);
            p.setId(1L);
            return p;
        });

        dataInitializer.run();

        verify(userService, times(2)).create(any());
        verify(companyService).create(any());
        verify(parkingService, times(3)).create(any());
        verify(parkingSpotService, times(15)).create(any());
    }

    @Test
    @DisplayName("run - Should not create company if test user is missing")
    void shouldNotCreateCompanyIfUserMissing() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());
        when(companyService.findAll()).thenReturn(Collections.emptyList());
        
        dataInitializer.run();

        verify(companyService, never()).create(any());
    }

    @Test
    @DisplayName("run - Should not create parkings if company is null")
    void shouldNotCreateParkingsIfCompanyNull() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(new User()));
        when(companyService.findAll()).thenReturn(Collections.emptyList()); 
        
        when(userService.findByEmail("test@test.com")).thenReturn(Optional.empty());
        
        when(parkingService.findAll()).thenReturn(Collections.emptyList());

        dataInitializer.run();

        verify(parkingService, never()).create(any());
    }
}
