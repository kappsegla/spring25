package se.iths.java24.spring25.events;

import org.springframework.context.ApplicationEvent;
import se.iths.java24.spring25.domain.entity.Playground;

public class PlaygroundAddedEvent extends ApplicationEvent {

    private final String userName;
    private final Playground playground;

    public PlaygroundAddedEvent(Object source, Playground playground, String userName) {
        super(source);
        this.playground = playground;
        this.userName = userName;
    }

    public Playground getPlayground() {
        return playground;
    }

    public String getUserName() {
        return userName;
    }
}
