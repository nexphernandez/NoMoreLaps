package com.nomorelaps.adapters.in.rest;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.nomorelaps.adapters.in.api.NotificationResponse;
import com.nomorelaps.adapters.mapper.NotificationMapper;
import com.nomorelaps.business.interfaces.INotificationService;
import com.nomorelaps.domain.models.Notification;

/**
 * Integration tests for NotificationController.
 * Verifies notification retrieval, status updates (marking as read), and deletion.
 * 
 * @author nexphernandez
 * @version 1.1.0
 */
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private INotificationService notificationService;

    @MockitoBean
    private NotificationMapper notificationMapper;

    private Notification sampleNotification;
    private NotificationResponse sampleResponse;

    @BeforeEach
    void setUp() {
        sampleNotification = new Notification(1L);
        sampleNotification.setMessage("Test Message");
        sampleNotification.setType("ALERT");
        sampleNotification.setIsRead(false);

        sampleResponse = new NotificationResponse();
        sampleResponse.setId(1L);
        sampleResponse.setMessage("Test Message");
        sampleResponse.setType("ALERT");
        sampleResponse.setIsRead(false);
    }

    @Test
    @DisplayName("GET /api/notifications/company/{companyId} - Success: Should return list of notifications")
    void shouldFindNotificationsByCompanyId() throws Exception {
        when(notificationService.findByCompanyId(10L)).thenReturn(List.of(sampleNotification));
        when(notificationMapper.toResponse(sampleNotification)).thenReturn(sampleResponse);

        mockMvc.perform(get("/api/notifications/company/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].message").value("Test Message"));
    }

    @Test
    @DisplayName("PATCH /api/notifications/{id}/read - Success: Should mark notification as read")
    void shouldMarkNotificationAsReadSuccessfully() throws Exception {
        sampleResponse.setIsRead(true);
        when(notificationService.markAsRead(1L)).thenReturn(sampleNotification);
        when(notificationMapper.toResponse(sampleNotification)).thenReturn(sampleResponse);

        mockMvc.perform(patch("/api/notifications/1/read"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.isRead").value(true));
    }

    @Test
    @DisplayName("PATCH /api/notifications/company/{companyId}/read-all - Success: Should mark all as read")
    void shouldMarkAllNotificationsAsReadSuccessfully() throws Exception {
        mockMvc.perform(patch("/api/notifications/company/10/read-all"))
                .andExpect(status().isOk());

        verify(notificationService).markAllAsRead(10L);
    }

    @Test
    @DisplayName("DELETE /api/notifications/{id} - Success: Should delete notification")
    void shouldDeleteNotificationSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/notifications/1"))
                .andExpect(status().isNoContent());

        verify(notificationService).deleteById(1L);
    }
}
