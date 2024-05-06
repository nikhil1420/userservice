package dev.nikhil.userservice.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class ValidateTokenRequestDto {
    private UUID userId;
    private String token;
}
