package org.example.relaxmapback.places;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.places.dto.PlaceResponse;
import org.springframework.data.domain.Page;
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

  @GetMapping
  public ResponseEntity<Page<Place>> getPlacesForUser(Authentication auth, Pageable pageable) {
    String email = auth.getName();

    return ResponseEntity.ok(placeService.getPlacesForUser(email, pageable));
  }

  @GetMapping("/all")
  public ResponseEntity<Page<Place>> getAllPlaces(Pageable pageable) {
    return ResponseEntity.ok(placeService.getAllPlaces(pageable));
  }

  @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  public ResponseEntity<PlaceResponse> createPlace(
          @RequestParam("name") String name,
          @RequestParam("place_type") String placeType,
          @RequestParam("region") String region,
          @RequestParam("file") MultipartFile file,
          Authentication auth
  ) throws IOException {
    Place place = placeService.createPlace(name, placeType, region, file, auth.getName());

    return ResponseEntity.ok(new PlaceResponse(place.getId(), place.getName(), place.getPlaceType(), place.getRegion(), place.getImageName()));
  }
}
