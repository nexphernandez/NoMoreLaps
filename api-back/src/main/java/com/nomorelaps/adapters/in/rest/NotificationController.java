package com.nomorelaps.adapters.in.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nomorelaps.adapters.in.api.NotificationResponse;
import com.nomorelaps.adapters.mapper.NotificationMapper;
import com.nomorelaps.business.interfaces.INotificationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * REST Controller for managing Notifications.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@RestController
@RequestMapping("/api/notifications")
@CrossOrigin(origins = "*")
@Tag(name = "Notifications", description = "Operations related to system notifications")
public class NotificationController {

    private final INotificationService notificationService;
    private final NotificationMapper notificationMapper;

    /**
     * Constructor for NotificationController.
     * 
     * @param notificationService domain service for notification management
     * @param notificationMapper mapper for converting between domain and DTOs
     */
    @Autowired
    public NotificationController(INotificationService notificationService, NotificationMapper notificationMapper) {
        this.notificationService = notificationService;
        this.notificationMapper = notificationMapper;
    }

    @GetMapping("/company/{companyId}")
    @Operation(summary = "Get notifications by company", description = "Retrieves all notifications for a specific company.")
    public ResponseEntity<List<NotificationResponse>> findByCompanyId(@PathVariable Long companyId) {
        List<NotificationResponse> responses = notificationService.findByCompanyId(companyId).stream()
                .map(notificationMapper::toResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    @PatchMapping("/{id}/read")
    @Operation(summary = "Mark notification as read", description = "Updates the status of a notification to 'read'.")
    public ResponseEntity<NotificationResponse> markAsRead(@PathVariable Long id) {
        return ResponseEntity.ok(notificationMapper.toResponse(notificationService.markAsRead(id)));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete notification", description = "Removes a notification from the system.")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        notificationService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
