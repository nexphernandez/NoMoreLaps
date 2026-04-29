package com.nomorelaps.adapters.in.api;

import java.util.Objects;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for smart parking recommendation requests.
 * Encapsulates the necessary request data sent by the client.
 *
 * @author nexphernandez
 *
 * @version 1.0.0
 */
public class SmartRecommendationRequest {

    private String destinationText;
    private Double latitude;
    private Double longitude;
    @NotBlank(message = "Start time is required")
    private String startTime;
    @NotNull(message = "Duration in hours is required")
    private Integer durationHours;
    @NotNull(message = "Radius in kilometers is required")
    private Integer radiusKm;

    /**
     * Empty constructor
     */
    public SmartRecommendationRequest() {
    }

    /**
     * Constructor with all request fields.
     *
     * @param destinationText destination text from calendar or user input
     *
     * @param latitude destination latitude
     *
     * @param longitude destination longitude
     *
     * @param startTime reservation start time in ISO local date-time format
     *
     * @param durationHours reservation duration in hours
     *
     * @param radiusKm search radius in kilometers
     */
    public SmartRecommendationRequest(
            String destinationText,
            Double latitude,
            Double longitude,
            String startTime,
            Integer durationHours,
            Integer radiusKm) {
        this.destinationText = destinationText;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startTime = startTime;
        this.durationHours = durationHours;
        this.radiusKm = radiusKm;
    }

    public String getDestinationText() {
        return this.destinationText;
    }

    public void setDestinationText(String destinationText) {
        this.destinationText = destinationText;
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

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public Integer getDurationHours() {
        return this.durationHours;
    }

    public void setDurationHours(Integer durationHours) {
        this.durationHours = durationHours;
    }

    public Integer getRadiusKm() {
        return this.radiusKm;
    }

    public void setRadiusKm(Integer radiusKm) {
        this.radiusKm = radiusKm;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartRecommendationRequest)) return false;
        SmartRecommendationRequest that = (SmartRecommendationRequest) o;
        return Objects.equals(destinationText, that.destinationText)
                && Objects.equals(startTime, that.startTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationText, startTime);
    }
}
