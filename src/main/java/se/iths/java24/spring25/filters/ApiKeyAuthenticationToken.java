package se.iths.java24.spring25.filters;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.List;

public class ApiKeyAuthenticationToken extends AbstractAuthenticationToken {
    private final String apiKey;

    public ApiKeyAuthenticationToken(String apiKey) {
        List<GrantedAuthority> authorities =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
        super(authorities);
        this.apiKey = apiKey;
        super.setAuthenticated(true);
    }

    public String getApiKey() {
        return apiKey;
    }

    @Override
    public Object getCredentials() {
        return null; // API key doesn't have "credentials" in the traditional sense
    }

    @Override
    public Object getPrincipal() {
        return apiKey; // Principal can simply be the API key itself
    }
}
