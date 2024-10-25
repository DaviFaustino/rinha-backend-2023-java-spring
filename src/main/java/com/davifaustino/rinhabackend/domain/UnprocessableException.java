package com.davifaustino.rinhabackend.domain;

public class UnprocessableException extends RuntimeException {
    
    public UnprocessableException(String message) {
        super(message);
    }
}
