package com.example.recommendation.exception;

public class LoginFailedException extends RuntimeException{
    public LoginFailedException(String message)
    {
        super(message);
    }
}
