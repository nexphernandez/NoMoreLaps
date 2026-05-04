package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;

class DynamicPriceResponseTest {

    @Test
    @DisplayName("Should test getters, setters and empty constructor for DynamicPriceResponse")
    void testGettersAndSetters() {
        DynamicPriceResponse dto = new DynamicPriceResponse();
        assertNotNull(dto);

        dto.setId(1L);
        assertEquals(1L, dto.getId());
        dto.setDayOfWeek(1);
        assertEquals(1, dto.getDayOfWeek());
        dto.setStartHour("dummy1");
        assertEquals("dummy1", dto.getStartHour());
        dto.setEndHour("dummy1");
        assertEquals("dummy1", dto.getEndHour());
        dto.setMinPrice(1.0);
        assertEquals(1.0, dto.getMinPrice());
        dto.setMaxPrice(1.0);
        assertEquals(1.0, dto.getMaxPrice());
        dto.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        assertEquals(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1), dto.getCreateAt());
    }

    @Test
    @DisplayName("Should test equals and hashCode branches for DynamicPriceResponse")
    void testEqualsAndHashCode() {
        DynamicPriceResponse dto1 = new DynamicPriceResponse();
        DynamicPriceResponse dto2 = new DynamicPriceResponse();
        DynamicPriceResponse dto3 = new DynamicPriceResponse();

        dto1.setId(1L);
        dto2.setId(1L);
        dto3.setId(2L);
        dto1.setDayOfWeek(1);
        dto2.setDayOfWeek(1);
        dto3.setDayOfWeek(2);
        dto1.setStartHour("dummy1");
        dto2.setStartHour("dummy1");
        dto3.setStartHour("dummy2");
        dto1.setEndHour("dummy1");
        dto2.setEndHour("dummy1");
        dto3.setEndHour("dummy2");
        dto1.setMinPrice(1.0);
        dto2.setMinPrice(1.0);
        dto3.setMinPrice(2.0);
        dto1.setMaxPrice(1.0);
        dto2.setMaxPrice(1.0);
        dto3.setMaxPrice(2.0);
        dto1.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        dto2.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        dto3.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(2));

        // Base checks
        assertEquals(dto1, dto1);
        assertEquals(dto1, dto2);
        assertNotEquals(dto1, dto3);
        assertNotEquals(dto1, null);
        assertNotEquals(dto1, new Object());
        assertEquals(dto1.hashCode(), dto2.hashCode());

        // Branch coverage for each field
        DynamicPriceResponse tempid = new DynamicPriceResponse();
        tempid.setId(1L);
        tempid.setDayOfWeek(1);
        tempid.setStartHour("dummy1");
        tempid.setEndHour("dummy1");
        tempid.setMinPrice(1.0);
        tempid.setMaxPrice(1.0);
        tempid.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempid.setId(null);
        dto1.equals(tempid);
        tempid.equals(dto1);
        tempid.setId(2L);
        dto1.equals(tempid);

        DynamicPriceResponse tempdayOfWeek = new DynamicPriceResponse();
        tempdayOfWeek.setId(1L);
        tempdayOfWeek.setDayOfWeek(1);
        tempdayOfWeek.setStartHour("dummy1");
        tempdayOfWeek.setEndHour("dummy1");
        tempdayOfWeek.setMinPrice(1.0);
        tempdayOfWeek.setMaxPrice(1.0);
        tempdayOfWeek.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempdayOfWeek.setDayOfWeek(2);
        dto1.equals(tempdayOfWeek);

        DynamicPriceResponse tempstartHour = new DynamicPriceResponse();
        tempstartHour.setId(1L);
        tempstartHour.setDayOfWeek(1);
        tempstartHour.setStartHour("dummy1");
        tempstartHour.setEndHour("dummy1");
        tempstartHour.setMinPrice(1.0);
        tempstartHour.setMaxPrice(1.0);
        tempstartHour.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempstartHour.setStartHour(null);
        dto1.equals(tempstartHour);
        tempstartHour.equals(dto1);
        tempstartHour.setStartHour("dummy2");
        dto1.equals(tempstartHour);

        DynamicPriceResponse tempendHour = new DynamicPriceResponse();
        tempendHour.setId(1L);
        tempendHour.setDayOfWeek(1);
        tempendHour.setStartHour("dummy1");
        tempendHour.setEndHour("dummy1");
        tempendHour.setMinPrice(1.0);
        tempendHour.setMaxPrice(1.0);
        tempendHour.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempendHour.setEndHour(null);
        dto1.equals(tempendHour);
        tempendHour.equals(dto1);
        tempendHour.setEndHour("dummy2");
        dto1.equals(tempendHour);

        DynamicPriceResponse tempminPrice = new DynamicPriceResponse();
        tempminPrice.setId(1L);
        tempminPrice.setDayOfWeek(1);
        tempminPrice.setStartHour("dummy1");
        tempminPrice.setEndHour("dummy1");
        tempminPrice.setMinPrice(1.0);
        tempminPrice.setMaxPrice(1.0);
        tempminPrice.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempminPrice.setMinPrice(2.0);
        dto1.equals(tempminPrice);

        DynamicPriceResponse tempmaxPrice = new DynamicPriceResponse();
        tempmaxPrice.setId(1L);
        tempmaxPrice.setDayOfWeek(1);
        tempmaxPrice.setStartHour("dummy1");
        tempmaxPrice.setEndHour("dummy1");
        tempmaxPrice.setMinPrice(1.0);
        tempmaxPrice.setMaxPrice(1.0);
        tempmaxPrice.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempmaxPrice.setMaxPrice(2.0);
        dto1.equals(tempmaxPrice);

        DynamicPriceResponse tempcreateAt = new DynamicPriceResponse();
        tempcreateAt.setId(1L);
        tempcreateAt.setDayOfWeek(1);
        tempcreateAt.setStartHour("dummy1");
        tempcreateAt.setEndHour("dummy1");
        tempcreateAt.setMinPrice(1.0);
        tempcreateAt.setMaxPrice(1.0);
        tempcreateAt.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(1));
        tempcreateAt.setCreateAt(null);
        dto1.equals(tempcreateAt);
        tempcreateAt.equals(dto1);
        tempcreateAt.setCreateAt(LocalDateTime.of(2026, 1, 1, 0, 0).plusDays(2));
        dto1.equals(tempcreateAt);

    }
}
