package com.nomorelaps.infrastructure.config;

import com.nomorelaps.adapters.in.soap.interfaces.*;
import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Apache CXF Configuration class.
 * Centralizes the publication of all 9 SOAP endpoints.
 * 
 * @author nexphernandez
 */
@Configuration
public class SoapConfig {

    private final Bus bus;
    
    private final IAuthSoapService authSoapService;
    private final ICompanySoapService companySoapService;
    private final IDynamicPriceSoapService dynamicPriceSoapService;
    private final IParkingSoapService parkingSoapService;
    private final IParkingSpotSoapService parkingSpotSoapService;
    private final IReservationSoapService reservationSoapService;
    private final IRoleSoapService roleSoapService;
    private final ISanctionSoapService sanctionSoapService;
    private final IUserSoapService userSoapService;

    @Autowired
    public SoapConfig(Bus bus, 
                      IAuthSoapService authSoapService,
                      ICompanySoapService companySoapService,
                      IDynamicPriceSoapService dynamicPriceSoapService,
                      IParkingSoapService parkingSoapService,
                      IParkingSpotSoapService parkingSpotSoapService,
                      IReservationSoapService reservationSoapService,
                      IRoleSoapService roleSoapService,
                      ISanctionSoapService sanctionSoapService,
                      IUserSoapService userSoapService) {
        this.bus = bus;
        this.authSoapService = authSoapService;
        this.companySoapService = companySoapService;
        this.dynamicPriceSoapService = dynamicPriceSoapService;
        this.parkingSoapService = parkingSoapService;
        this.parkingSpotSoapService = parkingSpotSoapService;
        this.reservationSoapService = reservationSoapService;
        this.roleSoapService = roleSoapService;
        this.sanctionSoapService = sanctionSoapService;
        this.userSoapService = userSoapService;
    }

    @Bean public Endpoint authEndpoint() { return publish(authSoapService, "/AuthService"); }
    @Bean public Endpoint companyEndpoint() { return publish(companySoapService, "/CompanyService"); }
    @Bean public Endpoint dynamicPriceEndpoint() { return publish(dynamicPriceSoapService, "/DynamicPriceService"); }
    @Bean public Endpoint parkingEndpoint() { return publish(parkingSoapService, "/ParkingService"); }
    @Bean public Endpoint parkingSpotEndpoint() { return publish(parkingSpotSoapService, "/ParkingSpotService"); }
    @Bean public Endpoint reservationEndpoint() { return publish(reservationSoapService, "/ReservationService"); }
    @Bean public Endpoint roleEndpoint() { return publish(roleSoapService, "/RoleService"); }
    @Bean public Endpoint sanctionEndpoint() { return publish(sanctionSoapService, "/SanctionService"); }
    @Bean public Endpoint userEndpoint() { return publish(userSoapService, "/UserService"); }

    private Endpoint publish(Object service, String path) {
        EndpointImpl endpoint = new EndpointImpl(bus, service);
        endpoint.publish(path);
        return endpoint;
    }
}
