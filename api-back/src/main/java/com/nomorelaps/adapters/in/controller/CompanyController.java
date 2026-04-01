package com.nomorelaps.adapters.in.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.CompanyRequest;
import com.nomorelaps.adapters.in.api.CompanyResponse;
import com.nomorelaps.adapters.mapper.CompanyMapper;
import com.nomorelaps.business.interfaces.ICompanyService;
import com.nomorelaps.domain.models.Company;

import jakarta.validation.Valid;

/**
 * REST Controller for Company endpoints.
 * Exposes CRUD operations for the Company entity via the API layer.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/companies")
public class CompanyController {

    private final ICompanyService companyService;
    private final CompanyMapper companyMapper;

    @Autowired
    public CompanyController(ICompanyService companyService, CompanyMapper companyMapper) {
        this.companyService = companyService;
        this.companyMapper = companyMapper;
    }

    /**
     * Creates a new company in the system.
     *
     * @param request The company data from the API.
     * @return The created company as a response DTO.
     */
    @PostMapping
    public ResponseEntity<CompanyResponse> create(@Valid @RequestBody CompanyRequest request) {
        Company domain = companyMapper.toDomainFromRequest(request);
        Company saved = companyService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(companyMapper.toResponse(saved));
    }

    /**
     * Finds a company by its unique identifier.
     *
     * @param id The company ID.
     * @return The found company or 404 if not found.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> findById(@PathVariable Long id) {
        return companyService.findById(id)
                .map(company -> ResponseEntity.ok(companyMapper.toResponse(company)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Finds a company by its email address.
     *
     * @param email The email to search for.
     * @return The found company or 404 if not found.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<CompanyResponse> findByEmail(@PathVariable String email) {
        return companyService.findByEmail(email)
                .map(company -> ResponseEntity.ok(companyMapper.toResponse(company)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Finds a company by its API key.
     *
     * @param apiKey The API key to search for.
     * @return The found company or 404 if not found.
     */
    @GetMapping("/apikey/{apiKey}")
    public ResponseEntity<CompanyResponse> findByApiKey(@PathVariable String apiKey) {
        return companyService.findByApiKey(apiKey)
                .map(company -> ResponseEntity.ok(companyMapper.toResponse(company)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates an existing company.
     *
     * @param id      The company ID to update.
     * @param request The updated company data.
     * @return The updated company.
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> update(@PathVariable Long id, @Valid @RequestBody CompanyRequest request) {
        Company domain = companyMapper.toDomainFromRequest(request);
        domain.setId(id);
        Company updated = companyService.update(domain);
        return ResponseEntity.ok(companyMapper.toResponse(updated));
    }

    /**
     * Deletes a company by its ID.
     *
     * @param id The company ID to delete.
     * @return 204 No Content.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
