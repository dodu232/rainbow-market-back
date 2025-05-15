package org.example.rm_back.auth;

public record UserPrincipal(String account, String roles) {

    public String getRoles() {
        return roles;
    }
}
