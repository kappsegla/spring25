package se.iths.java24.spring25;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.UUID;

@Service
public class UUIDService {

    private WebClient webClient;

    public UUIDService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Retryable(maxAttempts = 3, backoff = @Backoff(delay = 1000))
    public UUIDDto getUUID() {

        System.out.println("Trying to get status from httpbin");

            return webClient.get()
                    .uri("/status/400")
                    .retrieve()
                    .toEntity(UUIDDto.class)
                    .block().getBody();
    }

    @Recover
    public UUIDDto recover(){
        return new UUIDDto("Error when connecting");
    }

}
