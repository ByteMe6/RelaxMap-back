package org.example.relaxmapback.reviews.dto;

public record ReviewResponse(long id, String text, int rating, long userEmail) {
}
