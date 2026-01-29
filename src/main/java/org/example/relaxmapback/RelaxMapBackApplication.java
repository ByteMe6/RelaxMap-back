package org.example.relaxmapback;

import io.github.cdimascio.dotenv.Dotenv;
import org.example.relaxmapback.storage.StorageProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(StorageProperties.class)
public class RelaxMapBackApplication {
  public static void main(String[] args) {
    Dotenv dotenv = Dotenv.configure()
            .ignoreIfMissing()
            .load();

    dotenv.entries().forEach(entry -> System.setProperty(entry.getKey(), entry.getValue()));

    SpringApplication.run(RelaxMapBackApplication.class, args);
  }
}
