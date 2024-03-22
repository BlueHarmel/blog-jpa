package com.estsoft.blogjpa.dto;

public class AddUserRequest {
    private String email;
    private String password;

    public AddUserRequest(String mockEmail, String mockPw) {
        this.email = mockEmail;
        this.password = mockPw;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}