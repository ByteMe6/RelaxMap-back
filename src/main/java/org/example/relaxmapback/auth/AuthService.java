package org.example.relaxmapback.auth;

import lombok.RequiredArgsConstructor;
import org.example.relaxmapback.auth.dto.TokenResponse;
import org.example.relaxmapback.exceptions.users.InvalidCredentialsException;
import org.example.relaxmapback.exceptions.tokens.InvalidRefreshTokenException;
import org.example.relaxmapback.exceptions.tokens.NotRefreshTokenException;
import org.example.relaxmapback.exceptions.users.UserAlreadyExistsException;
import org.example.relaxmapback.jwt.JwtUtil;
import org.example.relaxmapback.users.User;
import org.example.relaxmapback.users.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtUtil jwtUtil;

  public TokenResponse refresh(String refreshToken) {
    if (!jwtUtil.isValid(refreshToken)) {
      throw new InvalidRefreshTokenException("Invalid refresh token");
    }

    if (!"refresh".equals(jwtUtil.extractType(refreshToken))) {
      throw new NotRefreshTokenException("Not refresh token");
    }

    String email = jwtUtil.extractEmail(refreshToken);
    String accessToken = jwtUtil.generateAccessToken(email);

    return new TokenResponse(accessToken, refreshToken);
  }

  public TokenResponse register(String name, String email, String password) {
    if (userRepository.findByEmail(email).isPresent()) {
      throw new UserAlreadyExistsException("User already exists");
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

  public TokenResponse login(String email, String password) {
    User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Invalid credentials"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new InvalidCredentialsException("Invalid credentials");
    }

    String access = jwtUtil.generateAccessToken(email);
    String refresh = jwtUtil.generateRefreshToken(email);

    return new TokenResponse(access, refresh);
  }
}
