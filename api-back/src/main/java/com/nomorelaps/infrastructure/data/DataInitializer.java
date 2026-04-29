package com.nomorelaps.infrastructure.data;

import com.nomorelaps.business.interfaces.*;
import com.nomorelaps.domain.models.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class DataInitializer implements CommandLineRunner {

    private final IRoleService roleService;
    private final IUserService userService;
    private final ICompanyService companyService;
    private final IParkingService parkingService;
    private final IParkingSpotService parkingSpotService;

    public DataInitializer(IRoleService roleService, IUserService userService,
            ICompanyService companyService, IParkingService parkingService,
            IParkingSpotService parkingSpotService) {
        this.roleService = roleService;
        this.userService = userService;
        this.companyService = companyService;
        this.parkingService = parkingService;
        this.parkingSpotService = parkingSpotService;
    }

    @Override
    public void run(String... args) throws Exception {
        // 1. Create Roles
        Role userRole = createRoleIfNotFound("USER");
        Role companyRole = createRoleIfNotFound("COMPANY");

        // 2. Create a Test Company User
        if (userService.findByEmail("company@test.com").isEmpty()) {
            User companyUser = new User();
            companyUser.setName("Company Manager");
            companyUser.setEmail("company@test.com");
            companyUser.setPassword("123456"); 
            companyUser.setRole(companyRole);
            companyUser.setCreateAt(LocalDateTime.now());
            userService.create(companyUser);
            System.out.println("DataInitializer: Company user created (company@test.com / 123456)");
        }

        // 3. Create a Test User
        if (userService.findByEmail("test@test.com").isEmpty()) {
            User user = new User();
            user.setName("Test User");
            user.setEmail("test@test.com");
            user.setPassword("123456"); // UserService will encode it
            user.setRole(userRole);
            user.setCalendarEnable(true);
            user.setCreateAt(LocalDateTime.now());
            userService.create(user);
            System.out.println("DataInitializer: Test user created (test@test.com / 123456)");
        }

        // 3. Create a Company (needed for parkings)
        Company company;
        Optional<User> testUser = userService.findByEmail("test@test.com");
        if (companyService.findAll().isEmpty() && testUser.isPresent()) {
            company = new Company();
            company.setName("NoMoreLaps Global");
            company.setApiKey("NML-TEST-KEY-2024");
            company.setEmail("info@nomorelaps.com");
            company.setPassword("company123");
            company.setPhone("912345678");
            company.setCif("B12345678");
            company.setRegisterDay(LocalDateTime.now());
            company.setUser(testUser.get());
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
