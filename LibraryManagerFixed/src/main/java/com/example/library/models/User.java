
package com.example.library.models;

import java.util.UUID;

public class User {
    private String id;
    private String username;
    private String passwordHash;
    private String fullName;
    private String role; // "ADMIN" or "USER"

    public User() {}

    public User(String username, String passwordHash, String fullName, String role) {
        this.id = UUID.randomUUID().toString();
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
        this.role = role;
    }

    public String getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getFullName() { return fullName; }
    public String getRole() { return role; }

    public void setUsername(String u) { this.username = u; }
    public void setPasswordHash(String p) { this.passwordHash = p; }
    public void setFullName(String f) { this.fullName = f; }
    public void setRole(String r) { this.role = r; }
}
