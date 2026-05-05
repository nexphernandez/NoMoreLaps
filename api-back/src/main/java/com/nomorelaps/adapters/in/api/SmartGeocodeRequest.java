package com.nomorelaps.adapters.in.api;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object (DTO) for geocoding requests.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez
 *
 * @version 1.0.0
 */
public class SmartGeocodeRequest {

    @NotBlank(message = "Query is required")
    private String query;

    /**
     * Empty constructor
     */
    public SmartGeocodeRequest() {
    }

    /**
     * Constructor with the query field.
     *
     * @param query destination query text
     */
    public SmartGeocodeRequest(String query) {
        this.query = query;
    }

    public String getQuery() {
        return this.query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartGeocodeRequest)) return false;
        SmartGeocodeRequest that = (SmartGeocodeRequest) o;
        return Objects.equals(query, that.query);
    }

    @Override
    public int hashCode() {
        return Objects.hash(query);
    }
}
