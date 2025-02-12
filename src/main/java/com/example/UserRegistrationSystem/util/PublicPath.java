package com.example.UserRegistrationSystem.util;

public enum PublicPath {
    AUTH("/auth"),
    API_AUTH("/api/v1/auth/**"),
    API_ARTICLE("/api/v1/article"),
    API_SEARCH("/api/v1/search"),
    API_PROF_SEARCH("/api/v1/profile/search"),
    API_ANSWERS("/api/v1/answers"),
    API_IMAGE("/api/v1/image"),
    API_HUBS("/api/v1/hubs");
    private final String type;

    PublicPath(String type) {
        this.type = type;
    }

    public String type() {
        return type;
    }
}
