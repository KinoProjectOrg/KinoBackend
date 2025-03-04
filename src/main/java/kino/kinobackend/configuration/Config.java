package kino.kinobackend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    @Bean
    public WebClient.Builder webClientBuilder () {
        return WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3")
                .defaultHeader("accept", "application/json")
                .defaultHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJkODY4NWE4ZDE4NDU2NDJiMmJkOGEzYWJiYzA5MzVjZSIsIm5iZiI6MTc0MTAwNzkxMS4wNzAwMDAyLCJzdWIiOiI2N2M1YWMyN2EzMjc3YWI0YTFlNzc2ODYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.4hv-TWsloPYf_C3sLYk7JQIJUP1COxe_i4qd1xzcNxM");
    }
}
