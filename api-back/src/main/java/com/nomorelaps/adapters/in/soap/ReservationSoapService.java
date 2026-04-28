package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.ReservationResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IReservationSoapService;
import com.nomorelaps.adapters.mapper.ReservationMapper;
import com.nomorelaps.business.interfaces.IReservationService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Reservation SOAP Service.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "ReservationSoapService",
    portName = "ReservationSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IReservationSoapService"
)
public class ReservationSoapService implements IReservationSoapService {

    private final IReservationService reservationService;
    private final ReservationMapper reservationMapper;

    @Autowired
    public ReservationSoapService(IReservationService reservationService, ReservationMapper reservationMapper) {
        this.reservationService = reservationService;
        this.reservationMapper = reservationMapper;
    }

    @Override
    public List<ReservationResponse> findByUserId(Long userId) {
        return reservationService.findByUserId(userId).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> findByParkingSpotId(Long spotId) {
        return reservationService.findByParkingSpotId(spotId).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<ReservationResponse> findByState(String state) {
        return reservationService.findByState(state).stream()
                .map(reservationMapper::toResponse)
                .collect(Collectors.toList());
    }
}
