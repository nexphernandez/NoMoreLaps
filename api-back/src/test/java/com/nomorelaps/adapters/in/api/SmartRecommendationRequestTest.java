package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SmartRecommendationRequestTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SmartRecommendationRequest")
    void testGettersAndSetters() {
        SmartRecommendationRequest dto = new SmartRecommendationRequest();
        assertNotNull(dto);

        dto.setDestinationText("dummy1");
        assertEquals("dummy1", dto.getDestinationText());
        dto.setLatitude(1.0);
        assertEquals(1.0, dto.getLatitude());
        dto.setLongitude(1.0);
        assertEquals(1.0, dto.getLongitude());
        dto.setStartTime("dummy1");
        assertEquals("dummy1", dto.getStartTime());
        dto.setDurationHours(1);
        assertEquals(1, dto.getDurationHours());
        dto.setRadiusKm(1.0);
        assertEquals(1.0, dto.getRadiusKm());
    }

    @Test
    @DisplayName("Should test parameterized constructor")
    void testParameterizedConstructor() {
        SmartRecommendationRequest dto = new SmartRecommendationRequest("Madrid", 40.0, -3.0, "10:00", 2, 5.0);
        assertEquals("Madrid", dto.getDestinationText());
        assertEquals(40.0, dto.getLatitude());
        assertEquals(-3.0, dto.getLongitude());
        assertEquals("10:00", dto.getStartTime());
        assertEquals(2, dto.getDurationHours());
        assertEquals(5.0, dto.getRadiusKm());
    }


    @Test
    @DisplayName("Should test equals and hashCode branches for SmartRecommendationRequest")
    void testEqualsAndHashCode() {
        SmartRecommendationRequest dto1 = new SmartRecommendationRequest();
        SmartRecommendationRequest dto2 = new SmartRecommendationRequest();
        SmartRecommendationRequest dto3 = new SmartRecommendationRequest();

        dto1.setDestinationText("dummy1");
        dto2.setDestinationText("dummy1");
        dto3.setDestinationText("dummy2");
        dto1.setLatitude(1.0);
        dto2.setLatitude(1.0);
        dto3.setLatitude(2.0);
        dto1.setLongitude(1.0);
        dto2.setLongitude(1.0);
        dto3.setLongitude(2.0);
        dto1.setStartTime("dummy1");
        dto2.setStartTime("dummy1");
        dto3.setStartTime("dummy2");
        dto1.setDurationHours(1);
        dto2.setDurationHours(1);
        dto3.setDurationHours(2);
        dto1.setRadiusKm(1.0);
        dto2.setRadiusKm(1.0);
        dto3.setRadiusKm(2.0);

        // Base checks
        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Branch coverage for each field
        SmartRecommendationRequest tempdestinationText = new SmartRecommendationRequest();
        tempdestinationText.setDestinationText("dummy1");
        tempdestinationText.setLatitude(1.0);
        tempdestinationText.setLongitude(1.0);
        tempdestinationText.setStartTime("dummy1");
        tempdestinationText.setDurationHours(1);
        tempdestinationText.setRadiusKm(1.0);
        tempdestinationText.setDestinationText(null);
        dto1.equals(tempdestinationText);
        tempdestinationText.equals(dto1);
        tempdestinationText.setDestinationText("dummy2");
        dto1.equals(tempdestinationText);

        SmartRecommendationRequest templatitude = new SmartRecommendationRequest();
        templatitude.setDestinationText("dummy1");
        templatitude.setLatitude(1.0);
        templatitude.setLongitude(1.0);
        templatitude.setStartTime("dummy1");
        templatitude.setDurationHours(1);
        templatitude.setRadiusKm(1.0);
        templatitude.setLatitude(null);
        dto1.equals(templatitude);
        templatitude.equals(dto1);
        templatitude.setLatitude(2.0);
        dto1.equals(templatitude);

        SmartRecommendationRequest templongitude = new SmartRecommendationRequest();
        templongitude.setDestinationText("dummy1");
        templongitude.setLatitude(1.0);
        templongitude.setLongitude(1.0);
        templongitude.setStartTime("dummy1");
        templongitude.setDurationHours(1);
        templongitude.setRadiusKm(1.0);
        templongitude.setLongitude(null);
        dto1.equals(templongitude);
        templongitude.equals(dto1);
        templongitude.setLongitude(2.0);
        dto1.equals(templongitude);

        SmartRecommendationRequest tempstartTime = new SmartRecommendationRequest();
        tempstartTime.setDestinationText("dummy1");
        tempstartTime.setLatitude(1.0);
        tempstartTime.setLongitude(1.0);
        tempstartTime.setStartTime("dummy1");
        tempstartTime.setDurationHours(1);
        tempstartTime.setRadiusKm(1.0);
        tempstartTime.setStartTime(null);
        dto1.equals(tempstartTime);
        tempstartTime.equals(dto1);
        tempstartTime.setStartTime("dummy2");
        dto1.equals(tempstartTime);

        SmartRecommendationRequest tempdurationHours = new SmartRecommendationRequest();
        tempdurationHours.setDestinationText("dummy1");
        tempdurationHours.setLatitude(1.0);
        tempdurationHours.setLongitude(1.0);
        tempdurationHours.setStartTime("dummy1");
        tempdurationHours.setDurationHours(1);
        tempdurationHours.setRadiusKm(1.0);
        tempdurationHours.setDurationHours(null);
        dto1.equals(tempdurationHours);
        tempdurationHours.equals(dto1);
        tempdurationHours.setDurationHours(2);
        dto1.equals(tempdurationHours);

        SmartRecommendationRequest tempradiusKm = new SmartRecommendationRequest();
        tempradiusKm.setDestinationText("dummy1");
        tempradiusKm.setLatitude(1.0);
        tempradiusKm.setLongitude(1.0);
        tempradiusKm.setStartTime("dummy1");
        tempradiusKm.setDurationHours(1);
        tempradiusKm.setRadiusKm(1.0);
        tempradiusKm.setRadiusKm(2.0);
        dto1.equals(tempradiusKm);

    }
}
