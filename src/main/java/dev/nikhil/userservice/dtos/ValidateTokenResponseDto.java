package dev.nikhil.userservice.dtos;

import dev.nikhil.userservice.models.SessionStatus;
import lombok.Data;


@Data
public class ValidateTokenResponseDto {
    private UserDto user;
    private SessionStatus sessionStatus;
}
