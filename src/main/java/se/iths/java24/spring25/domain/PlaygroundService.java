package se.iths.java24.spring25.domain;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import java.util.List;

@Service
@Transactional
public class PlaygroundService {

    PlaygroundRepository playgroundRepository;

    public PlaygroundService(PlaygroundRepository playgroundRepository) {
        this.playgroundRepository = playgroundRepository;
    }

    public List<Playground> getAllPlaygrounds() {
        return playgroundRepository.findAll();
    }

    public void addRandomPlaygrounds(int count){
        for (int i = 0; i < count; i++) {
            Playground playground = new Playground();
            playground.setName("Playground " + i);
            playgroundRepository.save(playground);
        }
    }
    
    public Playground addPlayground(Playground playground) {
        return playgroundRepository.save(playground);
    }
}
