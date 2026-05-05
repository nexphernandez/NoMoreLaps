package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.ParkingSpotResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IParkingSpotSoapService;
import com.nomorelaps.adapters.mapper.ParkingSpotMapper;
import com.nomorelaps.business.interfaces.IParkingSpotService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Parking Spot SOAP Service.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "ParkingSpotSoapService",
    portName = "ParkingSpotSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IParkingSpotSoapService"
)
public class ParkingSpotSoapService implements IParkingSpotSoapService {

    private final IParkingSpotService parkingSpotService;
    private final ParkingSpotMapper parkingSpotMapper;

    @Autowired
    public ParkingSpotSoapService(IParkingSpotService parkingSpotService, ParkingSpotMapper parkingSpotMapper) {
        this.parkingSpotService = parkingSpotService;
        this.parkingSpotMapper = parkingSpotMapper;
    }

    @Override
    public List<ParkingSpotResponse> findByParkingId(Long parkingId) {
        return parkingSpotService.findByParkingId(parkingId).stream()
                .map(parkingSpotMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingSpotResponse> findAvailableSpots(Long parkingId) {
        return parkingSpotService.findAvailableSpots(parkingId).stream()
                .map(parkingSpotMapper::toResponse)
                .collect(Collectors.toList());
    }
}
