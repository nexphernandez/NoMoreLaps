package com.nomorelaps.infrastructure.data;

import com.nomorelaps.business.interfaces.*;
import com.nomorelaps.domain.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IRoleService roleService;
    private final IUserService userService;
    private final ICompanyService companyService;
    private final IParkingService parkingService;
    private final IParkingSpotService parkingSpotService;
    private final INotificationService notificationService;
    private final IReservationService reservationService;
    private final ISanctionService sanctionService;
    private final IDynamicPriceService dynamicPriceService;

    public DataInitializer(IRoleService roleService, IUserService userService,
            ICompanyService companyService, IParkingService parkingService,
            IParkingSpotService parkingSpotService, INotificationService notificationService,
            IReservationService reservationService, ISanctionService sanctionService,
            IDynamicPriceService dynamicPriceService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.parkingService = parkingService;
        this.parkingSpotService = parkingSpotService;
        this.notificationService = notificationService;
        this.reservationService = reservationService;
        this.sanctionService = sanctionService;
        this.dynamicPriceService = dynamicPriceService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Roles
        Role userRole = createRoleIfNotFound("USER");
        Role companyRole = createRoleIfNotFound("COMPANY");

        // 2. Create a Test User (Will get ID 1)
        if (userService.findByEmail("test@test.com").isEmpty()) {
            User user = new User();
            user.setName("Test User");
            user.setEmail("test@test.com");
            user.setPassword("123456"); 
            user.setRole(userRole);
            user.setCalendarEnable(true);
            user.setCreateAt(LocalDateTime.now());
            userService.create(user);
            System.out.println("DataInitializer: Test user created with ID 1 (test@test.com / 123456)");
        }

        // 3. Create a Test Company User (Will get ID 2)
        if (userService.findByEmail("company@test.com").isEmpty()) {
            User companyUser = new User();
            companyUser.setName("Company Manager");
            companyUser.setEmail("company@test.com");
            companyUser.setPassword("123456"); 
            companyUser.setRole(companyRole);
            companyUser.setCreateAt(LocalDateTime.now());
            userService.create(companyUser);
            System.out.println("DataInitializer: Company user created with ID 2 (company@test.com / 123456)");
        }

        // 3. Create a Company (needed for parkings)
        Company company;
        Optional<User> companyUser = userService.findByEmail("company@test.com");
        if (companyService.findAll().isEmpty() && companyUser.isPresent()) {
            company = new Company();
            company.setName("NoMoreLaps Global");
            company.setApiKey("NML-TEST-KEY-2024");
            company.setEmail("info@nomorelaps.com");
            company.setPassword("company123");
            company.setPhone("912345678");
            company.setCif("B12345678");
            company.setRegisterDay(LocalDateTime.now());
            company.setUser(companyUser.get());
            company = companyService.create(company);
            System.out.println("DataInitializer: Default company created");
        } else {
            company = companyService.findAll().stream().findFirst().orElse(null);
        }

        // 4. Create Parkings if map is empty
        if (parkingService.findAll().isEmpty() && company != null) {
            // Parking 1: Puerta del Sol, Madrid
            createParking(company, "Sol Central Parking", "Plaza de la Puerta del Sol, Madrid", 40.4168, -3.7038);

            // Parking 2: Retiro, Madrid
            createParking(company, "Retiro Park & Go", "Calle de Alfonso XII, Madrid", 40.4153, -3.6844);

            // Parking 3: Plaza de España, Madrid
            createParking(company, "Gran Vía West", "Plaza de España, Madrid", 40.4234, -3.7122);

            System.out.println("DataInitializer: 3 sample parkings created in Madrid");
        }

        // 5. Create Sample Notifications for the company
        if (company != null && notificationService.findByCompanyId(company.getId()).isEmpty()) {
            seedSampleNotifications(company.getId());
        }

        // 6. Create Sample Reservations and Sanctions
        Optional<User> testUser = userService.findByEmail("test@test.com");
        if (testUser.isPresent() && reservationService.findByCompanyId(company.getId()).isEmpty()) {
            seedSampleReservations(testUser.get());
        }
    }

    private void seedSampleReservations(User user) {
        parkingService.findAll().forEach(parking -> {
            // Create a Dynamic Price for each parking
            DynamicPrice dp = new DynamicPrice();
            dp.setParking(parking);
            dp.setDayOfWeek(1); // Monday
            dp.setStartHour("08:00");
            dp.setEndHour("20:00");
            dp.setMinPrice(2.0);
            dp.setMaxPrice(5.0);
            dp.setCreateAt(LocalDateTime.now());
            dynamicPriceService.create(dp);

            // Get a spot
            List<ParkingSpot> spots = parkingSpotService.findByParkingId(parking.getId());
            if (!spots.isEmpty()) {
                // Reservation 1: Completed
                Reservation r1 = new Reservation();
                r1.setUser(user);
                r1.setParkingSpot(spots.get(0));
                r1.setStartTime(LocalDateTime.now().minusDays(2).withHour(10).withMinute(0));
                r1.setEndTime(LocalDateTime.now().minusDays(2).withHour(12).withMinute(0));
                r1.setPrice(5.0);
                r1.setBasePrice(5.0);
                r1.setState("COMPLETED");
                r1.setPaid(true);
                reservationService.create(r1);

                // Reservation 2: Active with potential sanction
                Reservation r2 = new Reservation();
                r2.setUser(user);
                r2.setParkingSpot(spots.get(1));
                r2.setStartTime(LocalDateTime.now().minusHours(1));
                r2.setEndTime(LocalDateTime.now().plusHours(1));
                r2.setPrice(3.0);
                r2.setBasePrice(3.0);
                r2.setState("ACTIVE");
                r2.setPaid(false);
                reservationService.create(r2);

                // Reservation 3: Past with Sanction
                Reservation r3 = new Reservation();
                r3.setUser(user);
                r3.setParkingSpot(spots.get(2));
                r3.setStartTime(LocalDateTime.now().minusDays(1).withHour(14).withMinute(0));
                r3.setEndTime(LocalDateTime.now().minusDays(1).withHour(15).withMinute(0));
                r3.setPrice(2.5);
                r3.setBasePrice(2.5);
                r3.setState("COMPLETED");
                r3.setPaid(true);
                Reservation savedR3 = reservationService.create(r3);

                Sanction s = new Sanction();
                s.setReservation(savedR3);
                s.setUser(user);
                s.setAmount(10.0);
                s.setReason("Exceso de tiempo (30 min)");
                s.setArrivalTime(LocalDateTime.now().minusDays(1).withHour(15).withMinute(30));
                s.setPaid(false);
                sanctionService.create(s);
            }
        });
        System.out.println("DataInitializer: Sample reservations, sanctions and dynamic prices created.");
    }

    private void seedSampleNotifications(Long companyId) {
        // Create 3 types of notifications
        Notification n1 = new Notification();
        n1.setMessage("Nueva reserva realizada en Sol Central Parking.");
        n1.setType("RESERVATION");
        n1.setCompanyId(companyId);
        n1.setRead(false);
        n1.setCreatedAt(LocalDateTime.now().minusHours(2));
        notificationService.create(n1);

        Notification n2 = new Notification();
        n2.setMessage("Sanción aplicada por exceso de tiempo en Plaza de España.");
        n2.setType("SANCTION");
        n2.setCompanyId(companyId);
        n2.setRead(false);
        n2.setCreatedAt(LocalDateTime.now().minusHours(5));
        notificationService.create(n2);

        Notification n3 = new Notification();
        n3.setMessage("Mantenimiento programado para el sistema de sensores.");
        n3.setType("CANCEL");
        n3.setCompanyId(companyId);
        n3.setRead(true);
        n3.setCreatedAt(LocalDateTime.now().minusDays(1));
        notificationService.create(n3);

        System.out.println("DataInitializer: Sample notifications created for company ID " + companyId);
    }

    private Role createRoleIfNotFound(String name) {
        return roleService.findAll().stream()
                .filter(r -> r.getName().equals(name))
                .findFirst()
                .orElseGet(() -> {
                    Role r = new Role();
                    r.setName(name);
                    return roleService.create(r);
                });
    }

    private void createParking(Company company, String name, String address, double lat, double lon) {
        Parking p = new Parking();
        p.setName(name);
        p.setAddress(address);
        p.setLatitude(lat);
        p.setLongitude(lon);
        p.setCompany(company);
        p.setOpeningTime(LocalDateTime.now().withHour(7).withMinute(0));
        p.setClosingTime(LocalDateTime.now().withHour(23).withMinute(30));
        p.setCreatedAt(LocalDateTime.now());
        final Parking savedParking = parkingService.create(p);

        // Create 5 spots for each parking
        for (int i = 1; i <= 5; i++) {
            ParkingSpot spot = new ParkingSpot();
            spot.setNumber(i);
            spot.setState(true); 
            spot.setRegisterDate(LocalDateTime.now());
            spot.setParking(savedParking);
            parkingSpotService.create(spot);
        }
        System.out.println("DataInitializer: 5 spots created for parking: " + name);
    }
}
