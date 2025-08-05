package com.FabulasDeSapo.AdventureForge.dto;

public class DtoTokenRequest {
    private String idToken;

    // Constructor vacío
    public DtoTokenRequest() {}

    // Getters y setters
    public String getToken() {
        return idToken;
    }

    public void setToken(String token) {
        this.idToken = token;
    }
}
