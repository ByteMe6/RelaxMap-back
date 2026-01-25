package org.example.relaxmapback.storage;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.unit.DataSize;

import java.util.List;

@ConfigurationProperties(prefix = "storage")
@Getter
@Setter
public class StorageProperties {
  private String uploadDir;
  private List<String> allowedMimeTypes;
  private DataSize maxFileSize;
}
