package se.iths.java24.spring25.events.listeners;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.security.authorization.event.AuthorizationDeniedEvent;
import org.springframework.security.authorization.event.AuthorizationGrantedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;

@Component
public class StandardEventsListener implements ApplicationListener<AuthorizationDeniedEvent> {

    @Override
    public void onApplicationEvent(AuthorizationDeniedEvent event) {
        // Get the HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) event.getSource();

        // Extract the failed URL
        String failedUrl = request.getRequestURL().toString();
        System.out.println("Authorization Denied URL: " + failedUrl);
    }

}
