package me.varnavsky.productreviewservice.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@ConfigurationProperties(prefix = "spring.security.user")
@Data
public class UserSecurityProperties {
    private String name;
    private String password;
    private List<String> roles;
}
