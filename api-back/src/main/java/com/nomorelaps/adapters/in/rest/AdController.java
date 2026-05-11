package com.nomorelaps.adapters.in.rest;

import com.nomorelaps.adapters.in.rest.dto.AdResponseDTO;
import com.nomorelaps.infrastructure.odoo.OdooClient;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/ads")
@Tag(name = "Ads", description = "Endpoints para la gestión de publicidad integrada con Odoo ERP")
public class AdController {

    private final OdooClient odooClient;

    public AdController(OdooClient odooClient) {
        this.odooClient = odooClient;
    }

    @GetMapping("/active")
    @Operation(summary = "Obtener anuncios activos desde Odoo ERP", 
               description = "Consulta el módulo personalizado de Odoo para obtener los banners y promociones vigentes.")
    public ResponseEntity<List<AdResponseDTO>> getActiveAds() {
        try {
            Integer uid = odooClient.authenticate();
            
            List<Map<String, Object>> odooAds = odooClient.getActiveAds(uid);
            
            List<AdResponseDTO> response = odooAds.stream().map(ad -> {
                return new AdResponseDTO(
                    getStringValue(ad, "name"),
                    getStringValue(ad, "ad_type"),
                    getStringValue(ad, "image_url"),
                    getStringValue(ad, "target_url")
                );
            }).collect(Collectors.toList());

            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    private String getStringValue(Map<String, Object> ad, String key) {
        Object value = ad.get(key);
        if (value == null || value instanceof Boolean) {
            return "";
        }
        return String.valueOf(value);
    }
}
