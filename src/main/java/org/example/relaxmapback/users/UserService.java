package org.example.relaxmapback.users;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.exceptions.users.UserNotExistsException;
import org.example.relaxmapback.users.dto.UserResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public UserResponse getUser(String email) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new UserNotExistsException("User is not exists"));

    return new UserResponse(user.getId(), user.getName(), user.getEmail());
  }
}
