package com.epam.esm.dto;

import java.util.Objects;

public class AuthenticationResponseDto {
    private String username;
    private String token;

    public AuthenticationResponseDto() {
    }

    public AuthenticationResponseDto(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthenticationResponseDto that = (AuthenticationResponseDto) o;
        return Objects.equals(username, that.username) && Objects.equals(token, that.token);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, token);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("AuthenticationDto{");
        sb.append("username='").append(username).append('\'');
        sb.append(", token='").append(token).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
