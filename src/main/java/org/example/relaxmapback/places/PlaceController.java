package org.example.relaxmapback.places;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.common.PageResponse;
import org.example.relaxmapback.places.dto.PlaceRequest;
import org.example.relaxmapback.places.dto.PlaceResponse;
import org.example.relaxmapback.places.dto.PlaceUpdateRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/places")
@RequiredArgsConstructor
public class PlaceController {
  private final PlaceService placeService;

  @GetMapping("/{id}")
  public ResponseEntity<PlaceResponse> getPlaceById(@PathVariable Long id) {
    return ResponseEntity.ok(placeService.getPlaceById(id));
  }

  @GetMapping("/for-user")
  public ResponseEntity<PageResponse<PlaceResponse>> getPlacesForUser(Authentication auth, Pageable pageable) {
    return ResponseEntity.ok(placeService.getPlacesForUser(auth.getName(), pageable));
  }

  @GetMapping("/all")
  public ResponseEntity<PageResponse<PlaceResponse>> getAllPlaces(Pageable pageable) {
    return ResponseEntity.ok(placeService.getAllPlaces(pageable));
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PlaceResponse> createPlace(
          @Valid @ModelAttribute PlaceRequest request,
          @NotNull(message = "Image is required") @RequestParam("file") MultipartFile file,
          Authentication auth
  ) throws IOException {
    return ResponseEntity.ok(placeService.createPlace(request, file, auth.getName()));
  }

  @PatchMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PlaceResponse> patchPlace(
          @PathVariable Long id,
          @Valid @ModelAttribute PlaceUpdateRequest request,
          @RequestParam(value = "file", required = false) MultipartFile file,
          Authentication auth
  ) throws IOException {
    return ResponseEntity.ok(placeService.patchPlace(
            id,
            request,
            file,
            auth.getName()
    ));
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePlace(
          @PathVariable Long id,
          Authentication auth
  ) throws IOException {
    placeService.deletePlace(id, auth.getName());

    return ResponseEntity.noContent().build();
  }
}
