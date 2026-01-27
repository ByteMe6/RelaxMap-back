package org.example.relaxmapback.users;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.auth.AuthService;
import org.example.relaxmapback.users.dto.UserResponse;
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
  public ResponseEntity<UserResponse> getUser(@PathVariable String email) {
    return ResponseEntity.ok(userService.getUser(email));
  }
}
