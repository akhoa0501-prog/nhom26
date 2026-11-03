package com.bookstore.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "app_users")
public class AppUser {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank @Column(nullable = false, unique = true, length = 60)
    private String username;
    @NotBlank @Column(nullable = false)
    private String password;
    @NotBlank @Column(nullable = false, length = 120)
    private String fullName;
    @Email @Column(unique = true, length = 150)
    private String email;
    @Column(nullable = false, length = 20)
    private String role = "USER";

    @Column(length=30) private String phone;
    @Column(length=300) private String address;
    @Column(length=255) private String avatarUrl;
    public String getPhone(){return phone;}
    public void setPhone(String v){phone=v;}
    public String getAddress(){return address;}
    public void setAddress(String v){address=v;}
    public String getAvatarUrl(){return avatarUrl;}
    public void setAvatarUrl(String v){avatarUrl=v;}
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}
