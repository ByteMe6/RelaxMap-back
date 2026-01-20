package org.example.relaxmapback.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtUtil {
  private static final String SECRET = "Bz56Xr3F4r4kwcrwK3JNNn0w1CqsXACQ44Bp3ShZ3n8=";
  private static final long ACCESS_EXP = 1000 * 60 * 15; // 15 mins
  private static final long REFRESH_EXP = 1000 * 60 * 60 * 24 * 7; // 7 days

  private SecretKey key;

  @PostConstruct
  public void init() {
    this.key = Keys.hmacShaKeyFor(SECRET.getBytes());
  }

  public String generateAccessToken(String email) {
    return Jwts.builder()
            .subject(email)
            .claim("type", "access")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + ACCESS_EXP))
            .signWith(key)
            .compact();
  }

  public String generateRefreshToken(String email) {
    return Jwts.builder()
            .subject(email)
            .claim("type", "refresh")
            .issuedAt(new Date())
            .expiration(new Date(System.currentTimeMillis() + REFRESH_EXP))
            .signWith(key)
            .compact();
  }

  public boolean isValid(String token) {
    try {
      Jwts.parser()
              .verifyWith(key)
              .build()
              .parseSignedClaims(token);

      return true;
    } catch (Exception e) {
      return false;
    }
  }

  public String extractEmail(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .getSubject();
  }

  public String extractType(String token) {
    return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
            .getPayload()
            .get("type", String.class);
  }
}
