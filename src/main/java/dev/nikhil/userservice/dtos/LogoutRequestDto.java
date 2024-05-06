package dev.nikhil.userservice.dtos;

import lombok.Data;

@Data
public class LogoutRequestDto {
    private Long userId;
    private String token;
}
