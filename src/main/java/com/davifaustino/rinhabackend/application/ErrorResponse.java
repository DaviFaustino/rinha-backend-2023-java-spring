package com.davifaustino.rinhabackend.application;

public record ErrorResponse (
    String message,
    String path,
    String method) {
}
