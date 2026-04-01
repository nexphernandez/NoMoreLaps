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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

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
@Tag(name = "Company", description = "Operations related to company management")
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
    @Operation(summary = "Create a new company", description = "Registers a new company in the platform.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Company created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "Conflict, email or CIF already exists")
    })
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
    @Operation(summary = "Find company by ID", description = "Retrieves company details by its unique identifier.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
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
    @Operation(summary = "Find company by email", description = "Retrieves company details searching by their registered email.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
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
    @Operation(summary = "Find company by API Key", description = "Retrieves company details using its unique API integration key.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company found"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
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
    @Operation(summary = "Update an existing company", description = "Updates company information based on details provided in the request body.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Company updated successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
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
    @Operation(summary = "Delete a company", description = "Removes a company from the platform by its ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Company deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Company not found")
    })
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        companyService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
