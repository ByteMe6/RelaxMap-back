package org.example.relaxmapback.users;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserRepository userRepository;

  public User getUser(String email) {
    return userRepository.findByEmail(email).orElse(null);
  }
}
