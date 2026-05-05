package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.SanctionResponse;
import com.nomorelaps.adapters.in.soap.interfaces.ISanctionSoapService;
import com.nomorelaps.adapters.mapper.SanctionMapper;
import com.nomorelaps.business.interfaces.ISanctionService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Sanction SOAP Service.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "SanctionSoapService",
    portName = "SanctionSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.ISanctionSoapService"
)
public class SanctionSoapService implements ISanctionSoapService {

    private final ISanctionService sanctionService;
    private final SanctionMapper sanctionMapper;

    @Autowired
    public SanctionSoapService(ISanctionService sanctionService, SanctionMapper sanctionMapper) {
        this.sanctionService = sanctionService;
        this.sanctionMapper = sanctionMapper;
    }

    @Override
    public List<SanctionResponse> findByUserId(Long userId) {
        return sanctionService.findByUserId(userId).stream()
                .map(sanctionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<SanctionResponse> findByReservationId(Long reservationId) {
        return sanctionService.findByReservationId(reservationId).stream()
                .map(sanctionMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SanctionResponse pay(Long id) {
        return sanctionMapper.toResponse(sanctionService.paySanction(id));
    }
}
