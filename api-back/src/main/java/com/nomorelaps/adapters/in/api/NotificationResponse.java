package com.nomorelaps.adapters.in.api;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response DTO for Notification.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
public class NotificationResponse {
    private Long id;
    private String message;
    private String type;
    @JsonProperty("isRead")
    private boolean isRead;
    private Long companyId;
    private LocalDateTime createdAt;

    /**
     * Empty constructor
     */
    public NotificationResponse() {
    }

    /**
     * Constructor with the notification primary key
     * 
     * @param id notification id
     */
    public NotificationResponse(Long id) {
        this.id = id;
    }

    /**
     * Constructor with all notification attributes.
     * 
     * @param id        notification id
     * @param message   notification message
     * @param type      notification type
     * @param read      notification read status
     * @param companyId identifier of the related company
     * @param createdAt creation timestamp
     */
    public NotificationResponse(Long id, String message, String type, boolean isRead, Long companyId, LocalDateTime createdAt) {
        this(id);
        this.message = message;
        this.type = type;
        this.isRead = isRead;
        this.companyId = companyId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("isRead")
    public boolean getIsRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
