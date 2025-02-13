package com.example.UserRegistrationSystem.dto;

public enum PublicPath {
    HOME(""),
    AUTH("/auth"),
    AUTH_METHOD("/auth/*");

    private final String type;

    PublicPath(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
