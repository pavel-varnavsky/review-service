package me.varnavsky.productreviewservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

  private static final String[] AUTH_WHITELIST = {
    "/v2/api-docs",
    "/swagger-resources",
    "/swagger-resources/**",
    "/configuration/ui",
    "/configuration/security",
    "/swagger-ui.html",
    "/webjars/**",
    "/v3/api-docs/**",
    "/swagger-ui/**",
    //
  };

  private final UserSecurityProperties userSecurityProperties;
  private final PasswordEncoder passwordEncoder;

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.inMemoryAuthentication()
        .withUser(userSecurityProperties.getName())
        .password(passwordEncoder.encode(userSecurityProperties.getPassword()))
        .authorities("ROLE_USER");
  }

  /**
   * Security filter.
   * I can't make it works for GET method without auth, so all method are locked with basic authentication.
   *
   * @param http HttpSecurity
   * @return Filtre chain
   * @throws Exception Any exception
   */
  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http.csrf()
        .disable()
        .authorizeHttpRequests(
            authorize ->
                authorize
                    .antMatchers(AUTH_WHITELIST)
                    .permitAll()
                    .antMatchers(HttpMethod.GET, "review/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .httpBasic();
    return http.build();
  }
}
