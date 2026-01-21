package org.example.relaxmapback.auth.dto;

public record ErrorResponse(String message, long timestamp, int status) {
}
