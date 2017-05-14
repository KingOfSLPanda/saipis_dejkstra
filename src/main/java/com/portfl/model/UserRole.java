package com.portfl.model;

public enum UserRole {
    ROLE_USER("User"),
    ROLE_ADMIN("Admin");

    private final String label;

    UserRole(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}