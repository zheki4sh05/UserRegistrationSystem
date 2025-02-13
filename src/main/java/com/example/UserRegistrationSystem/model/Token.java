package com.example.UserRegistrationSystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Table(name = "token",schema = "public")
@Getter
@Setter
public class Token {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "access_token")
    private String accessToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

}
