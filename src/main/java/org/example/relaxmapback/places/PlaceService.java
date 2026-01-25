package org.example.relaxmapback.places;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.storage.StorageProperties;
import org.example.relaxmapback.users.User;
import org.example.relaxmapback.users.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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

  public Page<Place> getPlacesForUser(String email, Pageable pageable) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    return placeRepository.findByUser(user, pageable);
  }

  public Page<Place> getAllPlaces(Pageable pageable) {
    return placeRepository.findAll(pageable);
  }

  public Place createPlace(String name, String placeType, String region, MultipartFile file, String email) throws IOException {
    validateFile(file);

    String extension = getFileExtension(file.getOriginalFilename());
    String filename = UUID.randomUUID() + extension;

    Path targetPath = Paths.get(storageProperties.getUploadDir()).resolve(filename);

    Files.createDirectories(targetPath.getParent());
    Files.copy(file.getInputStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);

    Place place = new Place();
    User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));

    place.setName(name);
    place.setPlaceType(placeType);
    place.setRegion(region);
    place.setImageName(filename);
    place.setUser(user);

    System.out.println(place);

    return placeRepository.save(place);
  }

  private void validateFile(MultipartFile file) {
    if (file.isEmpty()) {
      throw new RuntimeException("File is empty");
    }

    if (file.getSize() > storageProperties.getMaxFileSize().toBytes()) {
      throw new RuntimeException("File is too large");
    }

    String contentType = file.getContentType();

    if (contentType == null || !storageProperties.getAllowedMimeTypes().contains(contentType)) {
      throw new RuntimeException("Unsupported content type");
    }
  }

  private String getFileExtension(String fileName) {
    if (fileName == null || !fileName.contains(".")) return "";

    return fileName.substring(fileName.lastIndexOf("."));
  }
}
