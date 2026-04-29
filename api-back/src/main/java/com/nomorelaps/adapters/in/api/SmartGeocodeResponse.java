package com.nomorelaps.adapters.in.api;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a geocoding result in API responses.
 * Returns normalized address and resolved coordinates.
 *
 * @author nexphernandez
 *
 * @version 1.0.0
 */
public class SmartGeocodeResponse {

    private String formattedAddress;
    private Double latitude;
    private Double longitude;

    /**
     * Empty constructor
     */
    public SmartGeocodeResponse() {
    }

    /**
     * Constructor with all response fields.
     *
     * @param formattedAddress normalized address text
     *
     * @param latitude resolved latitude
     *
     * @param longitude resolved longitude
     */
    public SmartGeocodeResponse(String formattedAddress, Double latitude, Double longitude) {
        this.formattedAddress = formattedAddress;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getFormattedAddress() {
        return this.formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartGeocodeResponse)) return false;
        SmartGeocodeResponse that = (SmartGeocodeResponse) o;
        return Objects.equals(formattedAddress, that.formattedAddress)
                && Objects.equals(latitude, that.latitude)
                && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formattedAddress, latitude, longitude);
    }
}
