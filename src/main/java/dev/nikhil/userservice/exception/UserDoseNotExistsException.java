package dev.nikhil.userservice.exception;

public class UserDoseNotExistsException extends RuntimeException{
    public UserDoseNotExistsException(String message){
        super(message);
    }
}
