package com.example.StockDiplom.models.Enums.roles;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
    USER,
    ADMIN;

    Role(){
    }

    @Override
    public String getAuthority() {
        return name();
    }
}