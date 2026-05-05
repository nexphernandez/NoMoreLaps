package com.nomorelaps.adapters.in.api;

import java.util.List;
import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing smart parking recommendations in API responses.
 * Includes resolved destination context and ordered suggestion list.
 *
 * @author nexphernandez
 *
 * @version 1.0.0
 */
public class SmartRecommendationResponse {

    private String destinationText;
    private Double latitude;
    private Double longitude;
    private List<SmartRecommendationItemResponse> suggestions;

    /**
     * Empty constructor
     */
    public SmartRecommendationResponse() {
    }

    /**
     * Constructor with all response fields.
     *
     * @param destinationText normalized destination text
     *
     * @param latitude destination latitude
     *
     * @param longitude destination longitude
     *
     * @param suggestions ordered recommendation items
     */
    public SmartRecommendationResponse(
            String destinationText,
            Double latitude,
            Double longitude,
            List<SmartRecommendationItemResponse> suggestions) {
        this.destinationText = destinationText;
        this.latitude = latitude;
        this.longitude = longitude;
        this.suggestions = suggestions;
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

    public List<SmartRecommendationItemResponse> getSuggestions() {
        return this.suggestions;
    }

    public void setSuggestions(List<SmartRecommendationItemResponse> suggestions) {
        this.suggestions = suggestions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartRecommendationResponse)) return false;
        SmartRecommendationResponse that = (SmartRecommendationResponse) o;
        return Objects.equals(destinationText, that.destinationText)
                && Objects.equals(latitude, that.latitude)
                && Objects.equals(longitude, that.longitude);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destinationText, latitude, longitude);
    }
}
