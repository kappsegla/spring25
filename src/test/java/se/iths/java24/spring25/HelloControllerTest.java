package se.iths.java24.spring25;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import se.iths.java24.spring25.domain.PlaygroundService;

@WebMvcTest(HelloController.class)
class HelloControllerTest {

    @MockitoBean
    PlaygroundService playgroundService;

    @Test
    void getPlaygrounds() {


    }

}
