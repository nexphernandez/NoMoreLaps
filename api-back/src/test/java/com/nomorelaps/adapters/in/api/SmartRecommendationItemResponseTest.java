package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SmartRecommendationItemResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SmartRecommendationItemResponse")
    void testGettersAndSetters() {
        SmartRecommendationItemResponse dto = new SmartRecommendationItemResponse();
        assertNotNull(dto);

        dto.setParkingId(1L);
        assertEquals(1L, dto.getParkingId());
        dto.setParkingName("dummy1");
        assertEquals("dummy1", dto.getParkingName());
        dto.setAddress("dummy1");
        assertEquals("dummy1", dto.getAddress());
        dto.setDistanceKm(1.0);
        assertEquals(1.0, dto.getDistanceKm());
        dto.setAvailableSpots(1);
        assertEquals(1, dto.getAvailableSpots());
        dto.setSuggestedSpotId(1L);
        assertEquals(1L, dto.getSuggestedSpotId());
        dto.setSuggestedSpotNumber(1);
        assertEquals(1, dto.getSuggestedSpotNumber());
        dto.setStartTime("dummy1");
        assertEquals("dummy1", dto.getStartTime());
        dto.setEndTime("dummy1");
        assertEquals("dummy1", dto.getEndTime());
        dto.setPriceLabel("dummy1");
        assertEquals("dummy1", dto.getPriceLabel());
    }

    @Test
    @DisplayName("Should test equals and hashCode branches for SmartRecommendationItemResponse")
    void testEqualsAndHashCode() {
        SmartRecommendationItemResponse dto1 = new SmartRecommendationItemResponse();
        SmartRecommendationItemResponse dto2 = new SmartRecommendationItemResponse();
        SmartRecommendationItemResponse dto3 = new SmartRecommendationItemResponse();

        dto1.setParkingId(1L);
        dto2.setParkingId(1L);
        dto3.setParkingId(2L);
        dto1.setParkingName("dummy1");
        dto2.setParkingName("dummy1");
        dto3.setParkingName("dummy2");
        dto1.setAddress("dummy1");
        dto2.setAddress("dummy1");
        dto3.setAddress("dummy2");
        dto1.setDistanceKm(1.0);
        dto2.setDistanceKm(1.0);
        dto3.setDistanceKm(2.0);
        dto1.setAvailableSpots(1);
        dto2.setAvailableSpots(1);
        dto3.setAvailableSpots(2);
        dto1.setSuggestedSpotId(1L);
        dto2.setSuggestedSpotId(1L);
        dto3.setSuggestedSpotId(2L);
        dto1.setSuggestedSpotNumber(1);
        dto2.setSuggestedSpotNumber(1);
        dto3.setSuggestedSpotNumber(2);
        dto1.setStartTime("dummy1");
        dto2.setStartTime("dummy1");
        dto3.setStartTime("dummy2");
        dto1.setEndTime("dummy1");
        dto2.setEndTime("dummy1");
        dto3.setEndTime("dummy2");
        dto1.setPriceLabel("dummy1");
        dto2.setPriceLabel("dummy1");
        dto3.setPriceLabel("dummy2");

        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        SmartRecommendationItemResponse tempparkingId = new SmartRecommendationItemResponse();
        tempparkingId.setParkingId(1L);
        tempparkingId.setParkingName("dummy1");
        tempparkingId.setAddress("dummy1");
        tempparkingId.setDistanceKm(1.0);
        tempparkingId.setAvailableSpots(1);
        tempparkingId.setSuggestedSpotId(1L);
        tempparkingId.setSuggestedSpotNumber(1);
        tempparkingId.setStartTime("dummy1");
        tempparkingId.setEndTime("dummy1");
        tempparkingId.setPriceLabel("dummy1");
        tempparkingId.setParkingId(null);
        dto1.equals(tempparkingId);
        tempparkingId.equals(dto1);
        tempparkingId.setParkingId(2L);
        dto1.equals(tempparkingId);

        SmartRecommendationItemResponse tempparkingName = new SmartRecommendationItemResponse();
        tempparkingName.setParkingId(1L);
        tempparkingName.setParkingName("dummy1");
        tempparkingName.setAddress("dummy1");
        tempparkingName.setDistanceKm(1.0);
        tempparkingName.setAvailableSpots(1);
        tempparkingName.setSuggestedSpotId(1L);
        tempparkingName.setSuggestedSpotNumber(1);
        tempparkingName.setStartTime("dummy1");
        tempparkingName.setEndTime("dummy1");
        tempparkingName.setPriceLabel("dummy1");
        tempparkingName.setParkingName(null);
        dto1.equals(tempparkingName);
        tempparkingName.equals(dto1);
        tempparkingName.setParkingName("dummy2");
        dto1.equals(tempparkingName);

        SmartRecommendationItemResponse tempaddress = new SmartRecommendationItemResponse();
        tempaddress.setParkingId(1L);
        tempaddress.setParkingName("dummy1");
        tempaddress.setAddress("dummy1");
        tempaddress.setDistanceKm(1.0);
        tempaddress.setAvailableSpots(1);
        tempaddress.setSuggestedSpotId(1L);
        tempaddress.setSuggestedSpotNumber(1);
        tempaddress.setStartTime("dummy1");
        tempaddress.setEndTime("dummy1");
        tempaddress.setPriceLabel("dummy1");
        tempaddress.setAddress(null);
        dto1.equals(tempaddress);
        tempaddress.equals(dto1);
        tempaddress.setAddress("dummy2");
        dto1.equals(tempaddress);

        SmartRecommendationItemResponse tempdistanceKm = new SmartRecommendationItemResponse();
        tempdistanceKm.setParkingId(1L);
        tempdistanceKm.setParkingName("dummy1");
        tempdistanceKm.setAddress("dummy1");
        tempdistanceKm.setDistanceKm(1.0);
        tempdistanceKm.setAvailableSpots(1);
        tempdistanceKm.setSuggestedSpotId(1L);
        tempdistanceKm.setSuggestedSpotNumber(1);
        tempdistanceKm.setStartTime("dummy1");
        tempdistanceKm.setEndTime("dummy1");
        tempdistanceKm.setPriceLabel("dummy1");
        tempdistanceKm.setDistanceKm(null);
        dto1.equals(tempdistanceKm);
        tempdistanceKm.equals(dto1);
        tempdistanceKm.setDistanceKm(2.0);
        dto1.equals(tempdistanceKm);

        SmartRecommendationItemResponse tempavailableSpots = new SmartRecommendationItemResponse();
        tempavailableSpots.setParkingId(1L);
        tempavailableSpots.setParkingName("dummy1");
        tempavailableSpots.setAddress("dummy1");
        tempavailableSpots.setDistanceKm(1.0);
        tempavailableSpots.setAvailableSpots(1);
        tempavailableSpots.setSuggestedSpotId(1L);
        tempavailableSpots.setSuggestedSpotNumber(1);
        tempavailableSpots.setStartTime("dummy1");
        tempavailableSpots.setEndTime("dummy1");
        tempavailableSpots.setPriceLabel("dummy1");
        tempavailableSpots.setAvailableSpots(null);
        dto1.equals(tempavailableSpots);
        tempavailableSpots.equals(dto1);
        tempavailableSpots.setAvailableSpots(2);
        dto1.equals(tempavailableSpots);

        SmartRecommendationItemResponse tempsuggestedSpotId = new SmartRecommendationItemResponse();
        tempsuggestedSpotId.setParkingId(1L);
        tempsuggestedSpotId.setParkingName("dummy1");
        tempsuggestedSpotId.setAddress("dummy1");
        tempsuggestedSpotId.setDistanceKm(1.0);
        tempsuggestedSpotId.setAvailableSpots(1);
        tempsuggestedSpotId.setSuggestedSpotId(1L);
        tempsuggestedSpotId.setSuggestedSpotNumber(1);
        tempsuggestedSpotId.setStartTime("dummy1");
        tempsuggestedSpotId.setEndTime("dummy1");
        tempsuggestedSpotId.setPriceLabel("dummy1");
        tempsuggestedSpotId.setSuggestedSpotId(null);
        dto1.equals(tempsuggestedSpotId);
        tempsuggestedSpotId.equals(dto1);
        tempsuggestedSpotId.setSuggestedSpotId(2L);
        dto1.equals(tempsuggestedSpotId);

        SmartRecommendationItemResponse tempsuggestedSpotNumber = new SmartRecommendationItemResponse();
        tempsuggestedSpotNumber.setParkingId(1L);
        tempsuggestedSpotNumber.setParkingName("dummy1");
        tempsuggestedSpotNumber.setAddress("dummy1");
        tempsuggestedSpotNumber.setDistanceKm(1.0);
        tempsuggestedSpotNumber.setAvailableSpots(1);
        tempsuggestedSpotNumber.setSuggestedSpotId(1L);
        tempsuggestedSpotNumber.setSuggestedSpotNumber(1);
        tempsuggestedSpotNumber.setStartTime("dummy1");
        tempsuggestedSpotNumber.setEndTime("dummy1");
        tempsuggestedSpotNumber.setPriceLabel("dummy1");
        tempsuggestedSpotNumber.setSuggestedSpotNumber(null);
        dto1.equals(tempsuggestedSpotNumber);
        tempsuggestedSpotNumber.equals(dto1);
        tempsuggestedSpotNumber.setSuggestedSpotNumber(2);
        dto1.equals(tempsuggestedSpotNumber);

        SmartRecommendationItemResponse tempstartTime = new SmartRecommendationItemResponse();
        tempstartTime.setParkingId(1L);
        tempstartTime.setParkingName("dummy1");
        tempstartTime.setAddress("dummy1");
        tempstartTime.setDistanceKm(1.0);
        tempstartTime.setAvailableSpots(1);
        tempstartTime.setSuggestedSpotId(1L);
        tempstartTime.setSuggestedSpotNumber(1);
        tempstartTime.setStartTime("dummy1");
        tempstartTime.setEndTime("dummy1");
        tempstartTime.setPriceLabel("dummy1");
        tempstartTime.setStartTime(null);
        dto1.equals(tempstartTime);
        tempstartTime.equals(dto1);
        tempstartTime.setStartTime("dummy2");
        dto1.equals(tempstartTime);

        SmartRecommendationItemResponse tempendTime = new SmartRecommendationItemResponse();
        tempendTime.setParkingId(1L);
        tempendTime.setParkingName("dummy1");
        tempendTime.setAddress("dummy1");
        tempendTime.setDistanceKm(1.0);
        tempendTime.setAvailableSpots(1);
        tempendTime.setSuggestedSpotId(1L);
        tempendTime.setSuggestedSpotNumber(1);
        tempendTime.setStartTime("dummy1");
        tempendTime.setEndTime("dummy1");
        tempendTime.setPriceLabel("dummy1");
        tempendTime.setEndTime(null);
        dto1.equals(tempendTime);
        tempendTime.equals(dto1);
        tempendTime.setEndTime("dummy2");
        dto1.equals(tempendTime);

        SmartRecommendationItemResponse temppriceLabel = new SmartRecommendationItemResponse();
        temppriceLabel.setParkingId(1L);
        temppriceLabel.setParkingName("dummy1");
        temppriceLabel.setAddress("dummy1");
        temppriceLabel.setDistanceKm(1.0);
        temppriceLabel.setAvailableSpots(1);
        temppriceLabel.setSuggestedSpotId(1L);
        temppriceLabel.setSuggestedSpotNumber(1);
        temppriceLabel.setStartTime("dummy1");
        temppriceLabel.setEndTime("dummy1");
        temppriceLabel.setPriceLabel("dummy1");
        temppriceLabel.setPriceLabel(null);
        dto1.equals(temppriceLabel);
        temppriceLabel.equals(dto1);
        temppriceLabel.setPriceLabel("dummy2");
        dto1.equals(temppriceLabel);

    }
}
