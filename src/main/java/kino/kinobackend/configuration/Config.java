package kino.kinobackend.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class Config {

    // Doesn't work, so token is hardcoded below line 20 ...
//    @Value("${MOVIE_API}")
//    private String apiKey;

    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder()
                .baseUrl("https://api.themoviedb.org/3/")
                .defaultHeader("accept", "application/json")
                .defaultHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI0YTI2NzQ5ODE0M2Q4Y2NhYmM4NjhjNDM4YTVjYmQ0YyIsIm5iZiI6MTc0MTAwNzkxMS4wNzAwMDAyLCJzdWIiOiI2N2M1YWMyN2EzMjc3YWI0YTFlNzc2ODYiLCJzY29wZXMiOlsiYXBpX3JlYWQiXSwidmVyc2lvbiI6MX0.KKbGZN2azhKH-wJ93hDjXhKAuKRkwNTWh8MOSncEwrg");
    }

    @Bean
    public WebClient webClient(WebClient.Builder builder) {
        return builder.build();
    }
}
