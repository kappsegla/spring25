package se.iths.java24.spring25.web.controller;

import org.htmlunit.WebClient;
import org.htmlunit.html.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT )
@AutoConfigureMockMvc
@Testcontainers
@Transactional
public class PlaygroundControllerTest {

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));

    @Autowired
    private PlaygroundRepository playgroundRepository;

    @Autowired
    private WebTestClient webClient;

    @Autowired
    private WebClient htmlClient;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    void clearCaches() {
        cacheManager.getCacheNames().forEach(name ->
                Optional.ofNullable(cacheManager.getCache(name)).ifPresent(Cache::clear)
        );
    }

    @Test
    void viewPlaygroundsEndpoint() {
        // Prepare some test data
        playgroundRepository.saveAll(List.of(
                new Playground("Central Park"),
                new Playground("Riverside Park")
        ));

        //playgroundRepository.findAll().forEach(System.out::println);

        // Use WebTestClient to verify the response
        webClient.get().uri("/playgrounds/view")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .consumeWith(response -> {
                    String body = new String(response.getResponseBody());
                    assertThat(body).contains("Central Park");
                    assertThat(body).contains("Riverside Park");
                });
    }

    @Test
    @WithMockUser
    void addPlaygroundEndpoint() throws IOException {
        // Use HtmlUnit to simulate form submission
        HtmlPage page = htmlClient.getPage("/playgrounds/add");

        // Find form elements
        HtmlForm form = page.getForms().getFirst();
        HtmlTextInput nameInput = form.getInputByName("name");
        HtmlButton submitButton = (HtmlButton) form.getElementsByTagName("button").getFirst();

        // Fill out the form
        nameInput.setValueAttribute("New Playground");

        // Submit the form
        HtmlPage resultPage = submitButton.click();

        // Check for success message
        assertThat(((HtmlDivision) resultPage.getFirstByXPath("//div[@class='success-message']")).getTextContent())
                .contains("Playground added successfully!");

        // Verify database interaction
        List<Playground> playgrounds = playgroundRepository.findAll();
        assertThat(playgrounds)
                .anyMatch(p -> "New Playground".equals(p.getName()));
    }

    @Test
    void viewAllPlaygroundsPage() throws IOException {
        // Prepare test data
        playgroundRepository.saveAll(List.of(
                new Playground("Mountain View Park"),
                new Playground("Ocean Beach Playground")
        ));

        // Use HtmlUnit to verify page content
        HtmlPage page = htmlClient.getPage("/playgrounds/view");

        assertThat(page.getTitleText()).isEqualTo("View Playgrounds");

        // Check table contents
        String pageContent = page.asNormalizedText();
        assertThat(pageContent).contains("Mountain View Park");
        assertThat(pageContent).contains("Ocean Beach Playground");
    }

    @Test
    @WithMockUser(username="test")
    void addPlaygroundForm() throws IOException {
        // Verify the add playground form page
        HtmlPage page = htmlClient.getPage("/playgrounds/add");

        assertThat(page.getTitleText()).isEqualTo("Add Playground");

        // Check form elements
        HtmlForm form = page.getForms().getFirst();
        assertThat(form.getActionAttribute()).contains("/playgrounds/add");
        assertThat(form.getMethodAttribute()).isEqualToIgnoringCase("post");

        HtmlTextInput nameInput = form.getInputByName("name");
        assertThat(nameInput).isNotNull();
        assertThat(nameInput.hasAttribute("required")).isTrue();
    }
}
