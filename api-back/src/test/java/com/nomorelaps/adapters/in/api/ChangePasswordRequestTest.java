package com.nomorelaps.adapters.in.api;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ChangePasswordRequestTest {

    @Test
    @DisplayName("Should test constructor and getters/setters")
    void testChangePasswordRequest() {
        ChangePasswordRequest request = new ChangePasswordRequest("oldPass", "newPass");
        assertEquals("oldPass", request.getCurrentPassword());
        assertEquals("newPass", request.getNewPassword());

        ChangePasswordRequest request2 = new ChangePasswordRequest();
        request2.setCurrentPassword("curr");
        request2.setNewPassword("next");
        assertEquals("curr", request2.getCurrentPassword());
        assertEquals("next", request2.getNewPassword());
    }
}
