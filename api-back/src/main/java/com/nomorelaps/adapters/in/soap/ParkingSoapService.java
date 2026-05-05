package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.ParkingResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IParkingSoapService;
import com.nomorelaps.adapters.mapper.ParkingMapper;
import com.nomorelaps.business.interfaces.IParkingService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Parking SOAP Service.
 * Acts as an adapter between SOAP requests and the Business layer.
 * 
 * @author nexphernandez
 */
@Service
@WebService(serviceName = "ParkingSoapService", portName = "ParkingSoapPort", targetNamespace = "http://soap.in.adapters.nomorelaps.com/", endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IParkingSoapService")
public class ParkingSoapService implements IParkingSoapService {

    private final IParkingService parkingService;
    private final ParkingMapper parkingMapper;

    @Autowired
    public ParkingSoapService(IParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @Override
    public List<ParkingResponse> findAll() {
        return parkingService.findAll().stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ParkingResponse findById(Long id) {
        return parkingService.findById(id)
                .map(parkingMapper::toResponse)
                .orElse(null);
    }

    @Override
    public List<ParkingResponse> findByCompanyId(Long companyId) {
        return parkingService.findAllByCompanyId(companyId).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingResponse> search(String query) {
        return parkingService.searchByNameOrAddress(query).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ParkingResponse> findNearby(double lat, double lng, double radius) {
        return parkingService.findNearby(lat, lng, radius).stream()
                .map(parkingMapper::toResponse)
                .collect(Collectors.toList());
    }
}
