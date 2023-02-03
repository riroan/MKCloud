package com.openbox.backend.controller.dto;

import lombok.Data;

@Data
public class LoginRequest {
    public String id;
    public String password;
}
