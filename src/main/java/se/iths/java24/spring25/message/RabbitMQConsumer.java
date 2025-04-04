package se.iths.java24.spring25.message;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import se.iths.java24.spring25.domain.entity.Playground;

import java.util.logging.Logger;

@Component
public class RabbitMQConsumer {
    Logger log = Logger.getLogger(RabbitMQConsumer.class.getName());

    @RabbitListener(queues = "my-queue")
    public void consumeMessage(Playground playground) {
        log.info("Received message about a new playground: " + playground.getName());
    }
}
