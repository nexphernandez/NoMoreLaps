package com.nomorelaps.adapters.out.persistence.jpa;

import java.time.LocalDateTime;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.PrePersist;

/**
 * JPA Entity for Notification.
 * Maps the Notification domain object to the 'notifications' table in the database.
 *
 * @author nexphernandez DiazLuisAlejandro
 * @version 1.0.0
 */
@Entity
@Table(name = "notifications")
public class NotificationJpaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private String type; 

    @Column(name = "is_read", nullable = false)
    private boolean isRead = false;

    @Column(name = "company_id", nullable = false)
    private Long companyId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    /**
     * Empty constructor
     */
    public NotificationJpaEntity() {
    }

    /**
     * Constructor with the notification primary key
     * 
     * @param id notification id
     */
    public NotificationJpaEntity(Long id) {
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
    public NotificationJpaEntity(Long id, String message, String type, boolean isRead, Long companyId, LocalDateTime createdAt) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NotificationJpaEntity)) return false;
        NotificationJpaEntity that = (NotificationJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    /**
     * Life-cycle callback method called before the entity is persisted.
     * Automatically sets the creation date if it hasn't been set yet.
     */
    @PrePersist
    protected void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
