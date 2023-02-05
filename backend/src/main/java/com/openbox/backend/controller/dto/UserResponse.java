package com.openbox.backend.controller.dto;

import lombok.Data;

@Data
public class UserResponse {
    String id;
    Long capacity;
    Long used;
}
