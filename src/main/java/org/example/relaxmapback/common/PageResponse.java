package org.example.relaxmapback.common;

import java.util.List;

public record PageResponse<T>(List<T> content, long totalElements, int totalPages, int pageNumber, int pageSize) {
}
