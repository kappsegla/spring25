package se.iths.java24.spring25;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import static org.hamcrest.Matchers.is;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = "spring.main.allow-bean-definition-overriding=true")
@AutoConfigureMockMvc
@Testcontainers
//@ActiveProfiles("test")
class SpringBootIntegrationTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    MockMvc mockMvc;

    @Autowired
    PlaygroundRepository playgroundRepository;

    @BeforeEach
    void beforeEach() {
        var playground = new Playground();
        playground.setName("Playground 1");
        playgroundRepository.save(playground);
        playground = new Playground();
        playground.setName("Playground 2");
        playgroundRepository.save(playground);

    }

    @Test
    void getAllPlaygrounds() throws Exception {
        mockMvc.perform(get("/playgrounds"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Playground 1"))
                .andExpect(jsonPath("$[1].name").value("Playground 2"))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()", is(2)));

    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        CommandLineRunner runner() {
            return args -> {
                // do nothing
            };
        }
    }
}
