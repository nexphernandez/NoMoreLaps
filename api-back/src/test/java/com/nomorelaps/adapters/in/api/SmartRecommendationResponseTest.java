package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SmartRecommendationResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for SmartRecommendationResponse")
    void testGettersAndSetters() {
        SmartRecommendationResponse dto = new SmartRecommendationResponse();
        assertNotNull(dto);

        dto.setDestinationText("dummy1");
        assertEquals("dummy1", dto.getDestinationText());
        dto.setLatitude(1.0);
        assertEquals(1.0, dto.getLatitude());
        dto.setLongitude(1.0);
        assertEquals(1.0, dto.getLongitude());
        dto.setSuggestions(new java.util.ArrayList<>());
        assertEquals(new java.util.ArrayList<>(), dto.getSuggestions());
    }

    @Test
    @DisplayName("Should test equals and hashCode branches for SmartRecommendationResponse")
    void testEqualsAndHashCode() {
        SmartRecommendationResponse dto1 = new SmartRecommendationResponse();
        SmartRecommendationResponse dto2 = new SmartRecommendationResponse();
        SmartRecommendationResponse dto3 = new SmartRecommendationResponse();

        dto1.setDestinationText("dummy1");
        dto2.setDestinationText("dummy1");
        dto3.setDestinationText("dummy2");
        dto1.setLatitude(1.0);
        dto2.setLatitude(1.0);
        dto3.setLatitude(2.0);
        dto1.setLongitude(1.0);
        dto2.setLongitude(1.0);
        dto3.setLongitude(2.0);
        dto1.setSuggestions(new java.util.ArrayList<>());
        dto2.setSuggestions(new java.util.ArrayList<>());
        dto3.setSuggestions(new java.util.ArrayList<>());

        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        SmartRecommendationResponse tempdestinationText = new SmartRecommendationResponse();
        tempdestinationText.setDestinationText("dummy1");
        tempdestinationText.setLatitude(1.0);
        tempdestinationText.setLongitude(1.0);
        tempdestinationText.setSuggestions(new java.util.ArrayList<>());
        tempdestinationText.setDestinationText(null);
        dto1.equals(tempdestinationText);
        tempdestinationText.equals(dto1);
        tempdestinationText.setDestinationText("dummy2");
        dto1.equals(tempdestinationText);

        SmartRecommendationResponse templatitude = new SmartRecommendationResponse();
        templatitude.setDestinationText("dummy1");
        templatitude.setLatitude(1.0);
        templatitude.setLongitude(1.0);
        templatitude.setSuggestions(new java.util.ArrayList<>());
        templatitude.setLatitude(null);
        dto1.equals(templatitude);
        templatitude.equals(dto1);
        templatitude.setLatitude(2.0);
        dto1.equals(templatitude);

        SmartRecommendationResponse templongitude = new SmartRecommendationResponse();
        templongitude.setDestinationText("dummy1");
        templongitude.setLatitude(1.0);
        templongitude.setLongitude(1.0);
        templongitude.setSuggestions(new java.util.ArrayList<>());
        templongitude.setLongitude(null);
        dto1.equals(templongitude);
        templongitude.equals(dto1);
        templongitude.setLongitude(2.0);
        dto1.equals(templongitude);

        SmartRecommendationResponse tempsuggestions = new SmartRecommendationResponse();
        tempsuggestions.setDestinationText("dummy1");
        tempsuggestions.setLatitude(1.0);
        tempsuggestions.setLongitude(1.0);
        tempsuggestions.setSuggestions(new java.util.ArrayList<>());
        tempsuggestions.setSuggestions(null);
        dto1.equals(tempsuggestions);
        tempsuggestions.equals(dto1);
        tempsuggestions.setSuggestions(new java.util.ArrayList<>());
        dto1.equals(tempsuggestions);

    }
}
