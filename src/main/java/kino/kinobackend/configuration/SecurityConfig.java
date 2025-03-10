package kino.kinobackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


/*
    Denne her klasse konfigurerer sikkerheden vha. Spring Security.

    @Bean-annoteringerne bruges til at registrere metoder som Spring Beans, så de kan styres af Spring Container:

    - securityFilterChain(HttpSecurity http):
        Definerer sikkerhedsreglerne for HTTP-anmodninger. (Hvem må tilgå hvilke endpoints)

    - passwordEncoder():
        Opretter en BCryptPasswordEncoder-instans til hashing af kodeord.

    - authenticationManager(AuthenticationConfiguration authConfig):
        Leverer en `AuthenticationManager`-instans til autentifikation.
 */

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/registerEmployee", "/registerCustomer").permitAll() // disse sider er åbne for ALLE
                        .requestMatchers("/customer/**").hasRole("USER") // kun til kunder
//                        .requestMatchers("/reservation/**").hasRole("USER")
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/filmoperator/**").hasRole("FILM_OPERATOR")
                        .anyRequest().authenticated() // Alle andre endpoints kræver autentifikation. Den er muligvis unødvendig??
                );
        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // hasher koden sikkert med BCrypt
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager(); // håndterer autentifikation
    }
}