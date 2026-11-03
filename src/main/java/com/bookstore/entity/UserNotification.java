package com.bookstore.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;
@Entity
@Table(name = "user_notifications")
public class UserNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(length = 500)
    private String message;
    @Column(nullable = false)
    private boolean readFlag = false;
    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    public UserNotification() {
    }
    public Long getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public boolean isReadFlag() {
        return readFlag;
    }
    public void setReadFlag(boolean readFlag) {
        this.readFlag = readFlag;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}