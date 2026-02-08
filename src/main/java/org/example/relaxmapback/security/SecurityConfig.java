package org.example.relaxmapback.security;

import org.example.relaxmapback.jwt.JwtFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityConfig {
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtFilter jwtFilter, AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
    http
            .cors(Customizer.withDefaults())
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/auth/refresh").permitAll()
                    .requestMatchers("/auth/login").permitAll()
                    .requestMatchers("/auth/register").permitAll()
                    .requestMatchers("/auth/is-valid").permitAll()

                    .requestMatchers("/places/all").permitAll()
                    .requestMatchers("/places/for-user").permitAll()
                    .requestMatchers("/places/{id}").permitAll()


                    .requestMatchers("/reviews/all").permitAll()
                    .requestMatchers("/reviews/for-place/{id}").permitAll()

                    .requestMatchers("/images/**").permitAll()

                    .requestMatchers("/users/{email}").permitAll()

                    .requestMatchers("/swagger-ui/**").permitAll()
                    .requestMatchers("/v3/api-docs/**").permitAll()
                    .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex.authenticationEntryPoint(authenticationEntryPoint))
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    return request -> {
      CorsConfiguration config = new CorsConfiguration();

      String origin = request.getHeader("Origin");
      if (origin != null) {
        config.setAllowedOrigins(List.of(origin));
      } else {
        config.setAllowedOrigins(List.of());
      }

      config.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
      config.setAllowedHeaders(List.of("*"));
      config.setExposedHeaders(List.of("Authorization", "Content-Type"));
      config.setAllowCredentials(true);
      config.setMaxAge(3600L);

      return config;
    };
  }
}
