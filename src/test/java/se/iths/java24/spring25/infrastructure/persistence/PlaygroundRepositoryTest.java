package se.iths.java24.spring25.infrastructure.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.iths.java24.spring25.domain.entity.Playground;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Testcontainers
class PlaygroundRepositoryTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgresqlContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    PlaygroundRepository playgroundRepository;

    @BeforeEach
    void beforeEach() {
        var playground = new Playground();
        playground.setName("Playground");
        playgroundRepository.save(playground);
    }

    @Test
    void findAll() {
        var result = playgroundRepository.findAll();
        assertThat(result).hasSize(1);
    }

    @Test
    void findAllByNameReturnsOnlyPlaygroundsWithMatchingName() {
        var playground = new Playground();
        playground.setName("Test");
        playgroundRepository.save(playground);
        var result = playgroundRepository.findAllByName("Test");
        assertThat(result)
                .hasSize(1)
                .extracting(Playground::getName)
                .containsExactly("Test");
    }

}
