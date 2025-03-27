package se.iths.java24.spring25;

import org.htmlunit.WebClient;
import org.htmlunit.html.HtmlPage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment =
        SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Testcontainers
public class FullServerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    PlaygroundRepository playgroundRepository;

    @Autowired
    WebTestClient webClient;

    @Autowired
    WebClient client;

    //Test using reactive WebTestClient. Only downloads content from server
    @Test
    void fullServerTest() {
        webClient.get().uri("/playgrounds")
                .exchange()
                .expectStatus().isOk();
    }

    //Test html web pages using https://www.htmlunit.org, Is a full browser but without GUI.
    @Test
    void htmlEndpoint() throws IOException {
        HtmlPage page = client.getPage("/hello");
        assertThat(page.isHtmlPage()).isTrue();
        assertThat(page.getTitleText()).isEqualTo("Hello");
        assertThat(page.getElementById("header2").getTextContent()).isEqualTo("Smaller heading...");
    }


}
