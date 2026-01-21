package org.example.relaxmapback.common;

public record ErrorResponse(String message, long timestamp, int status) {
}
