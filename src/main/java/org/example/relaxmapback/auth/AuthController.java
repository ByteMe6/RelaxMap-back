package org.example.relaxmapback.auth;

import jakarta.validation.Valid;
import org.example.relaxmapback.auth.dto.RefreshRequest;
import org.example.relaxmapback.auth.dto.RegisterRequest;
import org.example.relaxmapback.auth.dto.TokenResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/refresh")
  public TokenResponse refresh(@RequestBody RefreshRequest request) {
    String refreshToken = request.refreshToken();

    return authService.refresh(refreshToken);
  }

  @PostMapping("/register")
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
    String name = request.name();
    String email = request.email();
    String password = request.password();

    if (authService.exists(email)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
              .body(Map.of("error", "User already exists"));
    }

    return ResponseEntity.ok(authService.register(name, email, password));
  }
}
