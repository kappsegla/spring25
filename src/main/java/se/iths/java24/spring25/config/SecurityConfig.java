package se.iths.java24.spring25.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationEventPublisher;
import org.springframework.security.authorization.SpringAuthorizationEventPublisher;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import se.iths.java24.spring25.filters.ApiKeyAuthenticationFilter;
import se.iths.java24.spring25.passkey.repository.PasskeyCredentialRepository;
import se.iths.java24.spring25.passkey.repository.DbPublicKeyCredentialUserEntityRepository;
import se.iths.java24.spring25.passkey.repository.DbUserCredentialRepository;
import se.iths.java24.spring25.passkey.repository.PasskeyUserRepository;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private static final Logger log = LoggerFactory.getLogger(SecurityConfig.class); // Add logger

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http, UserDetailsService userDetailsService) throws Exception {
        http
                .userDetailsService(userDetailsService)
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                .webAuthn((webAuthn) -> webAuthn
                        .rpName("My Spring Security Passkey Example")
                        .rpId("localhost") //Replace with the domainname of your application
                        .allowedOrigins("http://localhost:8080") //Replace with the URL of your application. Notice: this _MUST_ be an HTTPS URL with a valid certificate
                )
                //.addFilterAfter(new ApiKeyAuthenticationFilter(), LogoutFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/custom-login").permitAll()
                        .requestMatchers("/webauthn/**").permitAll()
                        .requestMatchers("/login", "/error").permitAll()
                        .requestMatchers("/playgrounds/view").permitAll()
                        .requestMatchers("/playgrounds/add").hasRole("USER")
                        .requestMatchers("/user").permitAll()
                        .requestMatchers("/graphiql").authenticated()
                        .requestMatchers("/hello").permitAll()
                        .requestMatchers("/info").authenticated()
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html", "/v3/api-docs.yaml").permitAll()
                        .requestMatchers("/ai/**").permitAll()
                        .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**", "/graphql")
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterAfter(new ApiKeyAuthenticationFilter(), LogoutFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/playgrounds").authenticated()
                        .requestMatchers(HttpMethod.POST, "/graphql").authenticated()
                        .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService(PasskeyUserRepository passkeyUserRepository) {
        log.info(">>> Creating CustomUserDetailsService bean instance NOW.");
        return new CustomUserDetailsService(passkeyUserRepository);
    }

    @Bean
    public AuthorizationEventPublisher authorizationEventPublisher
            (ApplicationEventPublisher applicationEventPublisher) {
        return new SpringAuthorizationEventPublisher(applicationEventPublisher);
    }

    @Bean
    PublicKeyCredentialUserEntityRepository userEntityRepository(PasskeyUserRepository passkeyUserRepository) {
        return new DbPublicKeyCredentialUserEntityRepository(passkeyUserRepository);
    }

    @Bean
    UserCredentialRepository userCredentialRepository(PasskeyUserRepository passkeyUserRepository, PasskeyCredentialRepository credentialRepository) {
        return new DbUserCredentialRepository(credentialRepository, passkeyUserRepository);
    }
}
