package org.example.relaxmapback.places;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.common.PageResponse;
import org.example.relaxmapback.places.dto.PlaceRequest;
import org.example.relaxmapback.places.dto.PlaceResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
  private final PlaceService placeService;

  @GetMapping
  public ResponseEntity<PageResponse<PlaceResponse>> getPlacesForUser(Authentication auth, Pageable pageable) {
    String email = auth.getName();

    Page<Place> placePage = placeService.getPlacesForUser(email, pageable);

    List<PlaceResponse> content = mapPageContent(placePage, place -> new PlaceResponse(
            place.getId(),
            place.getName(),
            place.getPlaceType(),
            place.getRegion(),
            place.getDescription(),
            place.getImageName()));

    PageResponse<PlaceResponse> response = new PageResponse<>(content, placePage.getTotalElements(), placePage.getTotalPages(), placePage.getNumber(), placePage.getSize());

    return ResponseEntity.ok(response);
  }

  @GetMapping("/all")
  public ResponseEntity<PageResponse<PlaceResponse>> getAllPlaces(Pageable pageable) {
    Page<Place> placePage = placeService.getAllPlaces(pageable);

    List<PlaceResponse> content = mapPageContent(placePage, place -> new PlaceResponse(
            place.getId(),
            place.getName(),
            place.getPlaceType(),
            place.getRegion(),
            place.getDescription(),
            place.getImageName()));

    PageResponse<PlaceResponse> response = new PageResponse<>(content, placePage.getTotalElements(), placePage.getTotalPages(), placePage.getNumber(), placePage.getSize());

    return ResponseEntity.ok(response);
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PlaceResponse> createPlace(
          @Valid @ModelAttribute PlaceRequest request,
          @NotNull(message = "Image is required") @RequestParam("file") MultipartFile file,
          Authentication auth
  ) throws IOException {
    Place place = placeService.createPlace(request, file, auth.getName());

    return ResponseEntity.ok(new PlaceResponse(place.getId(), place.getName(), place.getPlaceType(), place.getRegion(), place.getDescription(), place.getImageName()));
  }

  private <T, R> List<R> mapPageContent(Page<T> page, Function<T, R> mapper) {
    return page.getContent().stream()
            .map(mapper)
            .toList();
  }
}
