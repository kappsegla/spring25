package se.iths.java24.spring25.events.listeners;

import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import se.iths.java24.spring25.events.PlaygroundAddedEvent;

@Component
@Async
public class PlaygroundAddedListener implements ApplicationListener<PlaygroundAddedEvent> {
    @Override
    public void onApplicationEvent(PlaygroundAddedEvent event) {
        System.out.println("===========\n"+
                           "Playground Added Event Received: " +
                           event.getPlayground().getName() +
                           "\nUser : " + event.getUserName() +
                           "\n===========");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Done with the listener");
    }
}
