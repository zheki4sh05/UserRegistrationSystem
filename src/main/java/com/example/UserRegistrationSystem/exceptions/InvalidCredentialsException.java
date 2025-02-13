package com.example.UserRegistrationSystem.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String invalidUserEmailOrPassword) {
        super(invalidUserEmailOrPassword);
    }
}
