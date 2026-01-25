package org.example.relaxmapback.images;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.storage.StorageProperties;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@RequiredArgsConstructor
public class ImageService {
  private final StorageProperties storageProperties;

  public Resource getImage(String imageName) throws MalformedURLException {
    Path imagePath = Paths.get(storageProperties.getUploadDir()).resolve(imageName);

    return new UrlResource(imagePath.toUri());
  }

  public String probeContentType(String imageName) {
    try {
      Path imagePath = Paths.get(storageProperties.getUploadDir()).resolve(imageName);
      return Files.probeContentType(imagePath);
    } catch (IOException e) {
      return "application/octet-stream";
    }
  }
}
