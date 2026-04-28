package com.nomorelaps.adapters.in.soap;

import com.nomorelaps.adapters.in.api.RoleResponse;
import com.nomorelaps.adapters.in.soap.interfaces.IRoleSoapService;
import com.nomorelaps.adapters.mapper.RoleMapper;
import com.nomorelaps.business.interfaces.IRoleService;
import jakarta.jws.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Implementation of the Role SOAP Service.
 * 
 * @author nexphernandez
 */
@Service
@WebService(
    serviceName = "RoleSoapService",
    portName = "RoleSoapPort",
    targetNamespace = "http://soap.in.adapters.nomorelaps.com/",
    endpointInterface = "com.nomorelaps.adapters.in.soap.interfaces.IRoleSoapService"
)
public class RoleSoapService implements IRoleSoapService {

    private final IRoleService roleService;
    private final RoleMapper roleMapper;

    @Autowired
    public RoleSoapService(IRoleService roleService, RoleMapper roleMapper) {
        this.roleService = roleService;
        this.roleMapper = roleMapper;
    }

    @Override
    public List<RoleResponse> findAll() {
        return roleService.findAll().stream()
                .map(roleMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public RoleResponse findById(Long id) {
        return roleService.findById(id)
                .map(roleMapper::toResponse)
                .orElse(null);
    }
}
