package com.example.onroadservice;

public class ServiceProvider {
    private String email;
    private String providerId;

    private String name;
    // Add any additional fields as per your requirements

    public ServiceProvider() {
        // Default constructor required for Firebase
    }

    public ServiceProvider(String providerId,String email, String name) {
        this.email = email;
        this.name = name;
        this.providerId = providerId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }
    public String getProviderId() {
        return providerId;
    }
    // Add getters and setters for additional fields if needed
}
