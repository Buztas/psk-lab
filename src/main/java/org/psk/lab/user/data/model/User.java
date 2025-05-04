package org.psk.lab.user.data.model;

import jakarta.persistence.*;

import java.util.Objects;
import java.util.UUID;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uuid;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    private Role role;
    private Integer version;

    public User() {
    }

    public User(UUID uuid, String email, String password, Integer version, Role role) {
        this.uuid = uuid;
        this.email = email;
        this.password = password;
        this.version = version;
        this.role = role;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(uuid, user.uuid) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && role == user.role && Objects.equals(version, user.version);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid, email, password, role, version);
    }

    @Override
    public String toString() {
        return "User{" +
                "uuid=" + uuid +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", version=" + version +
                '}';
    }
}
