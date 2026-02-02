package org.example.relaxmapback.auth;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.auth.dto.*;
import org.example.relaxmapback.jwt.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
  private final AuthService authService;
  private final JwtUtil jwtUtil;

  @PostMapping("/refresh")
  public TokenResponse refresh(@RequestBody RefreshRequest request) {
    return authService.refresh(request.refreshToken());
  }

  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register(@RequestBody @Valid RegisterRequest request) {
    return ResponseEntity.ok(authService.register(
            request.name(),
            request.email(),
            request.password()
    ));
  }

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(@RequestBody @Valid LoginRequest request) {
    return ResponseEntity.ok(authService.login(
            request.email(),
            request.password()
    ));
  }

  @PatchMapping("/change-password")
  public ResponseEntity<Void> changePassword(
          @RequestBody @Valid PasswordRequest request,
          Authentication auth
  ) {
    authService.changePassword(request.oldPassword(), request.newPassword(), auth.getName());

    return ResponseEntity.ok().build();
  }

  @PatchMapping("/change-name")
  public ResponseEntity<Void> changeName(
          @RequestBody @Valid NameRequest request,
          Authentication auth
  ) {
    authService.changeName(request.newName(), auth.getName());

    return ResponseEntity.ok().build();
  }

  @PostMapping("/is-valid")
  public ResponseEntity<ValidResponse> isTokenValid(
          @RequestBody ValidRequest request
  ) {
    return ResponseEntity.ok(new ValidResponse(jwtUtil.isValid(request.token())));
  }
}
