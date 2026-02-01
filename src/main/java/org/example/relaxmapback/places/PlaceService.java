package org.example.relaxmapback.places;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.common.PageResponse;
import org.example.relaxmapback.exceptions.access.AccessDeniedException;
import org.example.relaxmapback.exceptions.files.EmptyFileException;
import org.example.relaxmapback.exceptions.files.TooLargeFileException;
import org.example.relaxmapback.exceptions.files.UnsupportedContentTypeException;
import org.example.relaxmapback.exceptions.places.PlaceNotExistsException;
import org.example.relaxmapback.exceptions.users.UserNotExistsException;
import org.example.relaxmapback.places.dto.PlaceRequest;
import org.example.relaxmapback.places.dto.PlaceResponse;
import org.example.relaxmapback.places.dto.PlaceUpdateRequest;
import org.example.relaxmapback.storage.StorageProperties;
import org.example.relaxmapback.users.User;
import org.example.relaxmapback.users.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlaceService {
  private final PlaceRepository placeRepository;
  private final UserRepository userRepository;
  private final StorageProperties storageProperties;

  public PlaceResponse getPlaceById(Long id) {
    Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotExistsException("Place is not exists"));

    return this.toResponse(place);
  }

  public PageResponse<PlaceResponse> getPlacesForUser(String email, Pageable pageable) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));
    Page<Place> placePage = placeRepository.findByUser(user, pageable);

    return this.toPageResponse(placePage);
  }

  public PageResponse<PlaceResponse> getAllPlaces(Pageable pageable) {
    Page<Place> placePage = placeRepository.findAll(pageable);

    return this.toPageResponse(placePage);
  }

  public PlaceResponse createPlace(PlaceRequest request, MultipartFile file, String email) throws IOException {
    String filename = this.writeFile(file);

    Place place = new Place();
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));

    place.setName(request.name());
    place.setPlaceType(request.placeType());
    place.setRegion(request.region());
    place.setDescription(request.description());
    place.setImageName(filename);
    place.setUser(user);

    placeRepository.save(place);

    return this.toResponse(place);
  }

  public PlaceResponse patchPlace(Long id, PlaceUpdateRequest request, MultipartFile file, String email) throws IOException {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));
    Place place = placeRepository.findById(id).orElseThrow(() -> new PlaceNotExistsException("Place is not exists"));

    if (!place.getUser().getId().equals(user.getId())) {
      throw new AccessDeniedException("You cannot change someone else's place");
    }

    if (request.name() != null) place.setName(request.name());
    if (request.placeType() != null) place.setPlaceType(request.placeType());
    if (request.region() != null) place.setRegion(request.region());
    if (request.description() != null) place.setDescription(request.description());
    if (file != null) {
      this.deleteFile(place.getImageName());

      String filename = this.writeFile(file);
      place.setImageName(filename);
    }

    placeRepository.save(place);

    return this.toResponse(place);
  }

  private PageResponse<PlaceResponse> toPageResponse(Page<Place> page) {
    return new PageResponse<>(
            page.getContent().stream().map(this::toResponse).toList(),
            page.getTotalElements(),
            page.getTotalPages(),
            page.getNumber(),
            page.getSize()
    );
  }

  private PlaceResponse toResponse(Place place) {
    return new PlaceResponse(
            place.getId(),
            place.getName(),
            place.getPlaceType(),
            place.getRegion(),
            place.getDescription(),
            place.getImageName()
    );
  }

  private String writeFile(MultipartFile file) throws IOException {
    validateFile(file);

    String extension = getFileExtension(file.getOriginalFilename());
    String filename = UUID.randomUUID() + extension;

    Path targetPath = Paths.get(storageProperties.getUploadDir()).resolve(filename);

    Files.createDirectories(targetPath.getParent());
    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    return filename;
  }

  private void deleteFile(String filename) throws IOException {
    Path targetPath = Paths.get(storageProperties.getUploadDir()).resolve(filename);

    boolean success = Files.deleteIfExists(targetPath);

    if (!success) throw new FileNotFoundException("File is not exists");
  }

  private void validateFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new EmptyFileException("File is empty");
    }

    if (file.getSize() > storageProperties.getMaxFileSize().toBytes()) {
      throw new TooLargeFileException("File is too large");
    }

    String contentType = file.getContentType();

    if (contentType == null || !storageProperties.getAllowedMimeTypes().contains(contentType)) {
      throw new UnsupportedContentTypeException("Unsupported content type");
    }
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) return "";

    return fileName.substring(fileName.lastIndexOf("."));
  }
}
