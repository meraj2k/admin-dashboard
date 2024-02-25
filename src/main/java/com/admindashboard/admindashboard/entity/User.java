package com.admindashboard.admindashboard.entity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.persistence.*;

import com.admindashboard.admindashboard.util.Role;
import com.admindashboard.admindashboard.util.SnowflakeIdGenerator;
import com.admindashboard.admindashboard.util.Status;

@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "user_id")
    private long userId;

    @Column(name = "email", nullable = false, length = 50,unique = true)
    private String email;
    @Column(name = "hash_key", nullable = false)
    private String hashKey;

    @Column(length = 50)
    private String name;

    @Column(length = 20, nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(length = 20)
    @Enumerated(EnumType.STRING)
    private Status status = Status.ACTIVE;

    @Column(name = "active_flag")
    private boolean activeFlag = true;

    public User() {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
        this.userId = snowflakeIdGenerator.generateUniqueId();
    }

    public User(String email, String hashKey, String name, Role role, Status status) {
        SnowflakeIdGenerator snowflakeIdGenerator = new SnowflakeIdGenerator(1, 1);
        this.userId = snowflakeIdGenerator.generateUniqueId();
        this.email = email;
        this.hashKey = generateHashKey(hashKey);
        this.name = name;
        this.role = role;
        this.status = status;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getHashKey() {
        return hashKey;
    }

    public void setHashKey(String hashKey) {
        this.hashKey = hashKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public boolean isActiveFlag() {
        return activeFlag;
    }

    public void setActiveFlag(boolean activeFlag) {
        this.activeFlag = activeFlag;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", hashKey='" + hashKey + '\'' +
                ", name='" + name + '\'' +
                ", role=" + role +
                ", status=" + status +
                ", activeFlag=" + activeFlag +
                '}';
    }




     //Generate hash key using SHA-256 algorithm
    public String generateHashKey(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
