package com.nomorelaps.adapters.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.nomorelaps.adapters.in.api.NotificationResponse;
import com.nomorelaps.adapters.mapper.NotificationMapper;
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Notification;
import com.nomorelaps.infrastructure.security.JwtService;

@WebMvcTest(NotificationController.class)
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private INotificationService notificationService;

    @MockBean
    private NotificationMapper notificationMapper;

    @MockBean
    private JwtService jwtService;

    @MockBean
    private com.nomorelaps.business.interfaces.ICompanyService companyService;

    @MockBean
    private com.nomorelaps.infrastructure.security.SecurityService securityService;

    @Test
    @DisplayName("GET /api/notifications/company/{companyId} - Should return list")
    void shouldFindByCompanyId() throws Exception {
        Notification domain = new Notification(1L);
        NotificationResponse response = new NotificationResponse();
        response.setId(1L);

        when(notificationService.findByCompanyId(10L)).thenReturn(List.of(domain));
        when(notificationMapper.toResponse(domain)).thenReturn(response);

        mockMvc.perform(get("/api/notifications/company/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L));
    }

    @Test
    @DisplayName("PATCH /api/notifications/{id}/read - Should mark as read")
    void shouldMarkAsRead() throws Exception {
        Notification domain = new Notification(1L);
        NotificationResponse response = new NotificationResponse();
        response.setId(1L);

        when(notificationService.markAsRead(1L)).thenReturn(domain);
        when(notificationMapper.toResponse(domain)).thenReturn(response);

        mockMvc.perform(patch("/api/notifications/1/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L));
    }

    @Test
    @DisplayName("PATCH /api/notifications/company/{companyId}/read-all - Should mark all as read")
    void shouldMarkAllAsRead() throws Exception {
        mockMvc.perform(patch("/api/notifications/company/10/read-all"))
                .andExpect(status().isOk());

        verify(notificationService).markAllAsRead(10L);
    }

    @Test
    @DisplayName("DELETE /api/notifications/{id} - Should delete")
    void shouldDelete() throws Exception {
        mockMvc.perform(delete("/api/notifications/1"))
                .andExpect(status().isNoContent());

        verify(notificationService).deleteById(1L);
    }
}
