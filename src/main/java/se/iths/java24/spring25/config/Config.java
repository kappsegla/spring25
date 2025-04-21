package se.iths.java24.spring25.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;
import se.iths.java24.spring25.passkey.repository.PasskeyUserRepository;
import se.iths.java24.spring25.user.domain.User;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Configuration
@Profile("!test")
@EnableCaching
@EnableRetry
@EnableAsync
public class Config {
    @Bean
    CommandLineRunner runner(PlaygroundRepository repository, PasskeyUserRepository passkeyUserRepository, PasswordEncoder encoder) {
        return args -> {
            if (repository.count() == 0) {
                Playground playground = new Playground();
                playground.setName("Test playground");
                repository.save(playground);
            }
            if( passkeyUserRepository.count() == 0) {
                User user = new User();
                user.setUsername("martin");
                user.setPassword(encoder.encode("password"));
                user.setExternalId(UUID.randomUUID().toString());
                user.setRoles(Set.of("USER", "ADMIN"));
                passkeyUserRepository.save(user);
            }
        };
    }

    @Bean
    WebClient webClient() {
        return WebClient.builder().baseUrl("https://httpbin.org/").build();
    }

}
