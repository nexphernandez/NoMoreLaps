package com.nomorelaps.adapters.in.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.DynamicPriceRequest;
import com.nomorelaps.adapters.in.api.DynamicPriceResponse;
import com.nomorelaps.adapters.mapper.DynamicPriceMapper;
import com.nomorelaps.business.interfaces.IDynamicPriceService;
import com.nomorelaps.domain.models.DynamicPrice;

import jakarta.validation.Valid;

/**
 * REST Controller for DynamicPrice endpoints.
 * Exposes CRUD operations for the DynamicPrice entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/dynamic-prices")
public class DynamicPriceController {

    private final IDynamicPriceService dynamicPriceService;
    private final DynamicPriceMapper dynamicPriceMapper;

    @Autowired
    public DynamicPriceController(IDynamicPriceService dynamicPriceService, DynamicPriceMapper dynamicPriceMapper) {
        this.dynamicPriceService = dynamicPriceService;
        this.dynamicPriceMapper = dynamicPriceMapper;
    }

    /**
     * Creates a new dynamic price rule.
     *
     * @param request The dynamic price data from the API.
     * @return The created dynamic price as a response DTO.
     */
    @PostMapping
    public ResponseEntity<DynamicPriceResponse> create(@Valid @RequestBody DynamicPriceRequest request) {
        DynamicPrice domain = dynamicPriceMapper.toDomainFromRequest(request);
        DynamicPrice saved = dynamicPriceService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(dynamicPriceMapper.toResponse(saved));
    }

    /**
     * Finds a dynamic price by its unique identifier.
     *
     * @param id The dynamic price ID.
     * @return The found dynamic price or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DynamicPriceResponse> findById(@PathVariable Long id) {
        return dynamicPriceService.findById(id)
                .map(dp -> ResponseEntity.ok(dynamicPriceMapper.toResponse(dp)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Retrieves all dynamic prices for a specific parking lot.
     *
     * @param parkingId The parking lot ID.
     * @return A list of dynamic prices for the parking.
     */
    @GetMapping("/parking/{parkingId}")
    public ResponseEntity<List<DynamicPriceResponse>> findByParkingId(@PathVariable Long parkingId) {
        List<DynamicPriceResponse> responses = dynamicPriceService.findByParkingId(parkingId).stream()
                .map(dynamicPriceMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    /**
     * Updates an existing dynamic price.
     *
     * @param id      The dynamic price ID to update.
     * @param request The updated dynamic price data.
     * @return The updated dynamic price.
     */
    @PutMapping("/{id}")
    public ResponseEntity<DynamicPriceResponse> update(@PathVariable Long id, @Valid @RequestBody DynamicPriceRequest request) {
        DynamicPrice domain = dynamicPriceMapper.toDomainFromRequest(request);
        domain.setId(id);
        DynamicPrice updated = dynamicPriceService.update(domain);
        return ResponseEntity.ok(dynamicPriceMapper.toResponse(updated));
    }

    /**
     * Deletes a dynamic price by its ID.
     *
     * @param id The dynamic price ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        dynamicPriceService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
