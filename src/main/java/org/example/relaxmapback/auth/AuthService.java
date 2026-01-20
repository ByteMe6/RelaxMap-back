package org.example.relaxmapback.auth;

import org.example.relaxmapback.auth.dto.TokenResponse;
import org.example.relaxmapback.jwt.JwtUtil;
import org.example.relaxmapback.users.User;
import org.example.relaxmapback.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtUtil = jwtUtil;
  }

  public TokenResponse refresh(String refreshToken) {
    if (!jwtUtil.isValid(refreshToken)) {
      throw new RuntimeException("Invalid refresh token");
    }

    if (!"refresh".equals(jwtUtil.extractType(refreshToken))) {
      throw new RuntimeException("Not refresh token");
    }

    String email = jwtUtil.extractEmail(refreshToken);
    String accessToken = jwtUtil.generateAccessToken(email);

    return new TokenResponse(accessToken, refreshToken);
  }

  public TokenResponse register(String name, String email, String password) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new RuntimeException("User already exists");
    }

    User user = new User();
    user.setName(name);
    user.setEmail(email);
    user.setPassword(passwordEncoder.encode(password));

    userRepository.save(user);

    String access = jwtUtil.generateAccessToken(email);
    String refresh = jwtUtil.generateRefreshToken(email);

    return new TokenResponse(access, refresh);
  }

  public boolean exists(String email) {
    User user = userRepository.findByEmail(email).orElse(null);

    return user != null;
  }
}
