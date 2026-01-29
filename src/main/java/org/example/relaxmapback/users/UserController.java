package org.example.relaxmapback.users;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.exceptions.users.IdOrEmailRequiredException;
import org.example.relaxmapback.users.dto.UserResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
  private final UserService userService;

  @GetMapping("/info")
  public ResponseEntity<UserResponse> getUser(
          @RequestParam(required = false) Long id,
          @RequestParam(required = false) String email
  ) {
    if (id != null) return ResponseEntity.ok(userService.getUserById(id));
    if (email != null) return ResponseEntity.ok(userService.getUserByEmail(email));

    throw new IdOrEmailRequiredException("Id or email is required");
  }

  @DeleteMapping("/delete-account")
  public ResponseEntity<Void> deleteUser(Authentication auth) {
    userService.deleteUser(auth.getName());

    return ResponseEntity.noContent().build();
  }
}
