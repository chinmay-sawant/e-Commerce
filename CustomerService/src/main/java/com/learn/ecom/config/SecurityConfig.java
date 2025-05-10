package com.learn.ecom.config;

    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
    import static org.springframework.security.config.Customizer.withDefaults;
    import org.springframework.security.config.annotation.web.builders.HttpSecurity;
    import org.springframework.security.web.SecurityFilterChain;

    /**
     * Configures our application with Spring Security to restrict access to our API endpoints.
     */
    @Configuration
    @Profile("!test") // This ensures the configuration is not loaded when the "test" profile is active
    public class SecurityConfig {

@Bean
public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    /*
    This is where we configure the security required for our endpoints and setup our app to serve as
    an OAuth2 Resource Server, using JWT validation.
    */
    return http
            .authorizeHttpRequests((authorize) -> authorize
                .requestMatchers("/api/public").permitAll()
                .requestMatchers("/swagger-ui.html", "/swagger-ui/**", "/v3/api-docs/**").permitAll()
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable()) 
            .cors(withDefaults())
            .oauth2ResourceServer(oauth2 -> oauth2
                .jwt(withDefaults())
            )
            .build();
}
    }