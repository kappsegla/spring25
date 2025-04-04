package se.iths.java24.spring25.domain;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import se.iths.java24.spring25.PlaygroundService;
import se.iths.java24.spring25.domain.entity.Playground;
import se.iths.java24.spring25.events.PlaygroundAddedEvent;
import se.iths.java24.spring25.infrastructure.persistence.PlaygroundRepository;

import java.util.List;

@Service
@Transactional
class PlaygroundServiceImpl implements PlaygroundService {
    Logger log = LoggerFactory.getLogger(PlaygroundServiceImpl.class);

    PlaygroundRepository playgroundRepository;

    ApplicationEventPublisher eventPublisher;

    RabbitTemplate rabbitTemplate;

    public PlaygroundServiceImpl(PlaygroundRepository playgroundRepository, ApplicationEventPublisher eventPublisher, RabbitTemplate rabbitTemplate) {
        this.playgroundRepository = playgroundRepository;
        this.eventPublisher = eventPublisher;
        this.rabbitTemplate = rabbitTemplate;
    }

    @Cacheable("allPlaygrounds")
    public List<Playground> getAllPlaygrounds() {
        log.info("Fetching all playgrounds");
        return playgroundRepository.findAll();
    }

    public void addRandomPlaygrounds(int count) {
        for (int i = 0; i < count; i++) {
            Playground playground = new Playground();
            playground.setName("Playground " + i);
            playgroundRepository.save(playground);
        }
    }

    @CacheEvict(value = "allPlaygrounds", allEntries = true)
    public Playground addPlayground(Playground playground) {
        var pg = playgroundRepository.save(playground);
        //Publish PlaygroundAddedEvent
        var event = new PlaygroundAddedEvent(this, pg, SecurityContextHolder.getContext().getAuthentication().getName());
        eventPublisher.publishEvent(event);

        rabbitTemplate.convertAndSend("my-exchange", "routing-key", pg);

        return pg;
    }
}
