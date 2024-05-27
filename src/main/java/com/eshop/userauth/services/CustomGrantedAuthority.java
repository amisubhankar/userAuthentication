package com.eshop.userauth.services;

import com.eshop.userauth.models.Role;
import org.springframework.security.core.GrantedAuthority;

public class CustomGrantedAuthority implements GrantedAuthority {

    public String role;

    public CustomGrantedAuthority() {
    }

    public CustomGrantedAuthority(Role role) {
        this.role = role.getName();
    }

    @Override
    public String getAuthority() {
        return role;
    }
}
