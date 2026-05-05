package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IDynamicPriceSoapService;
import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the DynamicPrice SOAP Service.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "DynamicPriceSoapService",
    portName = "DynamicPriceSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IDynamicPriceSoapService"
)
public class DynamicPriceSoapService implements IDynamicPriceSoapService {

    private final IDynamicPriceService dynamicPriceService;
    private final DynamicPriceMapper dynamicPriceMapper;

    @Autowired
    public DynamicPriceSoapService(IDynamicPriceService dynamicPriceService, DynamicPriceMapper dynamicPriceMapper) {
        this.dynamicPriceService = dynamicPriceService;
        this.dynamicPriceMapper = dynamicPriceMapper;
    }

    @Override
    public List<DynamicPriceResponse> findAll() {
        return dynamicPriceService.findAll().stream()
                .map(dynamicPriceMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DynamicPriceResponse findById(Long id) {
        return dynamicPriceService.findById(id)
                .map(dynamicPriceMapper::toResponse)
                .orElse(null);
    }

    @Override
    public List<DynamicPriceResponse> findByParkingId(Long parkingId) {
        return dynamicPriceService.findByParkingId(parkingId).stream()
                .map(dynamicPriceMapper::toResponse)
                .collect(Collectors.toList());
    }
}
