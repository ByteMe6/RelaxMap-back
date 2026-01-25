package org.example.relaxmapback.users;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.auth.AuthService;
import org.example.relaxmapback.common.ErrorResponse;
import org.example.relaxmapback.users.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;
  private final AuthService authService;

  @GetMapping("/{email}")
  @ApiResponses({
          @ApiResponse(responseCode = "200", content = @Content(schema = @Schema(implementation = UserResponse.class))),
          @ApiResponse(responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
  })
  public ResponseEntity<?> getUser(@PathVariable String email) {
    if (!authService.exists(email)) {
      return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse("User does not exists", System.currentTimeMillis(), 404));
    }

    User user = userService.getUser(email);

    return ResponseEntity.ok(new UserResponse(user.getId(), user.getName(), user.getEmail()));
  }
}
