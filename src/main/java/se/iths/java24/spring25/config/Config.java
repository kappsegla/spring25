package se.iths.java24.spring25.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.reactive.function.client.WebClient;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

@Configuration
@Profile("!test")
@EnableCaching
@EnableRetry
public class Config {
    @Bean
    CommandLineRunner runner(PlaygroundRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Playground playground = new Playground();
                playground.setName("Test playground");
                repository.save(playground);
            }
        };
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("https://httpbin.org/").build();
    }

}
