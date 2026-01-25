package org.example.relaxmapback;

import org.example.relaxmapback.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class RelaxMapBackApplication {
  public static void main(String[] args) {
    SpringApplication.run(RelaxMapBackApplication.class, args);
  }
}
