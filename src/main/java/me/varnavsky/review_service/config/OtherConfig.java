package me.varnavsky.review_service.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class OtherConfig {

  /**
   * Put PasswordEncoder bean to another configuration because if it created in
   * SecurityConfiguration then there is problem with circular references.
   *
   * @return PasswordEncoder
   */
  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}
