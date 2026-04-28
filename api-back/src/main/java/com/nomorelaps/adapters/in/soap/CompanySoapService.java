package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.in.soap.interfaces.ICompanySoapService;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.business.interfaces.ICompanyService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of the Company SOAP Service.
 * Manages corporate data retrieval for SOAP clients.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "CompanySoapService",
    portName = "CompanySoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.ICompanySoapService"
)
public class CompanySoapService implements ICompanySoapService {

    private final ICompanyService companyService;
    private final CompanyMapper companyMapper;

    @Autowired
    public CompanySoapService(ICompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    @Override
    public List<CompanyResponse> findAll() {
        return companyService.findAll().stream()
                .map(companyMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CompanyResponse findById(Long id) {
        return companyService.findById(id)
                .map(companyMapper::toResponse)
                .orElse(null);
    }
}
