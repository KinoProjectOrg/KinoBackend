package kino.kinobackend.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    private final Environment env;

    // Constructor Injection - ensures `env` is properly initialized
    @Autowired
    public WebClientConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public WebClient.Builder webClientBuilder() {

        String apiKey = env.getProperty("MOVIE_API");

        return WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .defaultHeader("accept", "application/json")
                .defaultHeader("Authorization", "Bearer " + apiKey );
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
