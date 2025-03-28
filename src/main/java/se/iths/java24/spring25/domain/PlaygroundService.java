package se.iths.java24.spring25.domain;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.filters.ApiKeyAuthenticationToken;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import java.security.Principal;
import java.util.List;

@Service
@Transactional
public class PlaygroundService {
    Logger log = LoggerFactory.getLogger(PlaygroundService.class);

    PlaygroundRepository playgroundRepository;

    public PlaygroundService(PlaygroundRepository playgroundRepository) {
        this.playgroundRepository = playgroundRepository;
    }

    @Cacheable("allPlaygrounds")
    public List<Playground> getAllPlaygrounds() {
        log.info("Fetching all playgrounds");
        return playgroundRepository.findAll();
    }

    public void addRandomPlaygrounds(int count){
        for (int i = 0; i < count; i++) {
            Playground playground = new Playground();
            playground.setName("Playground " + i);
            playgroundRepository.save(playground);
        }
    }

    @CacheEvict(value = "allPlaygrounds", allEntries = true)
    public Playground addPlayground(Playground playground) {
        return playgroundRepository.save(playground);
    }
}
