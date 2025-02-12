package com.example.UserRegistrationSystem.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.*;

import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name="user_role",schema = "public")
public class UserRole implements GrantedAuthority {

    @Column(name = "id")
    @Id
    private UUID uuid;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private User user;

    @Column(name = "name")
    private String role;

    @Override
    public String getAuthority() {
        return role;
    }
}
