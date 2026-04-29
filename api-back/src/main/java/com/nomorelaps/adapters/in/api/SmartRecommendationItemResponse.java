package com.nomorelaps.adapters.in.api;

import java.util.Objects;

/**
 * Data Transfer Object (DTO) representing a single smart parking recommendation item in API responses.
 * Encapsulates the structured data sent back to the client.
 *
 * @author nexphernandez
 *
 * @version 1.0.0
 */
public class SmartRecommendationItemResponse {

    private Long parkingId;
    private String parkingName;
    private String address;
    private Double distanceKm;
    private Integer availableSpots;
    private Long suggestedSpotId;
    private Integer suggestedSpotNumber;
    private String startTime;
    private String endTime;
    private String priceLabel;

    /**
     * Empty constructor
     */
    public SmartRecommendationItemResponse() {
    }

    /**
     * Constructor with all recommendation fields.
     *
     * @param parkingId suggested parking identifier
     *
     * @param parkingName suggested parking commercial name
     *
     * @param address suggested parking address
     *
     * @param distanceKm computed distance from destination
     *
     * @param availableSpots currently available spots count
     *
     * @param suggestedSpotId chosen spot identifier to reserve
     *
     * @param suggestedSpotNumber physical spot number
     *
     * @param startTime suggested reservation start time
     *
     * @param endTime suggested reservation end time
     *
     * @param priceLabel informational price message
     */
    public SmartRecommendationItemResponse(
            Long parkingId,
            String parkingName,
            String address,
            Double distanceKm,
            Integer availableSpots,
            Long suggestedSpotId,
            Integer suggestedSpotNumber,
            String startTime,
            String endTime,
            String priceLabel) {
        this.parkingId = parkingId;
        this.parkingName = parkingName;
        this.address = address;
        this.distanceKm = distanceKm;
        this.availableSpots = availableSpots;
        this.suggestedSpotId = suggestedSpotId;
        this.suggestedSpotNumber = suggestedSpotNumber;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priceLabel = priceLabel;
    }

    public Long getParkingId() {
        return this.parkingId;
    }

    public void setParkingId(Long parkingId) {
        this.parkingId = parkingId;
    }

    public String getParkingName() {
        return this.parkingName;
    }

    public void setParkingName(String parkingName) {
        this.parkingName = parkingName;
    }

    public String getAddress() {
        return this.address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getDistanceKm() {
        return this.distanceKm;
    }

    public void setDistanceKm(Double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Integer getAvailableSpots() {
        return this.availableSpots;
    }

    public void setAvailableSpots(Integer availableSpots) {
        this.availableSpots = availableSpots;
    }

    public Long getSuggestedSpotId() {
        return this.suggestedSpotId;
    }

    public void setSuggestedSpotId(Long suggestedSpotId) {
        this.suggestedSpotId = suggestedSpotId;
    }

    public Integer getSuggestedSpotNumber() {
        return this.suggestedSpotNumber;
    }

    public void setSuggestedSpotNumber(Integer suggestedSpotNumber) {
        this.suggestedSpotNumber = suggestedSpotNumber;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return this.endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPriceLabel() {
        return this.priceLabel;
    }

    public void setPriceLabel(String priceLabel) {
        this.priceLabel = priceLabel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SmartRecommendationItemResponse)) return false;
        SmartRecommendationItemResponse that = (SmartRecommendationItemResponse) o;
        return Objects.equals(parkingId, that.parkingId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(parkingId);
    }
}
