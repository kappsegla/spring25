package se.iths.java24.spring25.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PlaygroundServiceTest {

    @InjectMocks
    PlaygroundService playgroundService;

    @Mock
    PlaygroundRepository playgroundRepository;

//    @BeforeEach
//    void beforeEach() {
//        playgroundRepository = mock(PlaygroundRepository.class);
//        playgroundService = new PlaygroundService(playgroundRepository);
//    }


    @Test
    void getAllPlaygrounds() {
        when(playgroundRepository.findAll()).thenReturn(List.of());
        assertThat(playgroundService.getAllPlaygrounds()).isEmpty();
    }

}
