package dev.nikhil.userservice.models;

import lombok.Data;


public enum SessionStatus {
    ACTIVE,
    ENDED,
    EXPIRED,
    INVALID,
    LOGGED_OUT
}
