package org.example.relaxmapback.auth;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.relaxmapback.auth.dto.*;
import org.example.relaxmapback.common.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Register success",
                  content = @Content(schema = @Schema(implementation = TokenResponse.class))),
          @ApiResponse(responseCode = "409", description = "User already exists",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<?> register(@RequestBody @Valid RegisterRequest request) {
    String name = request.name();
    String email = request.email();
    String password = request.password();

    if (authService.exists(email)) {
      return ResponseEntity.status(HttpStatus.CONFLICT)
              .body(new ErrorResponse("User already exists", System.currentTimeMillis(), 409));
    }

    return ResponseEntity.ok(authService.register(name, email, password));
  }

  @PostMapping("/login")
  @ApiResponses({
          @ApiResponse(responseCode = "200", description = "Login success",
                  content = @Content(schema = @Schema(implementation = TokenResponse.class))),
          @ApiResponse(responseCode = "401", description = "Invalid credentials",
                  content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
    String email = request.email();
    String password = request.password();

    if (!authService.exists(email)) {
      return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
              .body(new ErrorResponse("Invalid credentials", System.currentTimeMillis(), 401));
    }

    return ResponseEntity.ok(authService.login(email, password));
  }
}
