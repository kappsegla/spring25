package se.iths.java24.spring25.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import se.iths.java24.spring25.filters.ApiKeyAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    @Order(2)
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .formLogin( Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults())
                //.addFilterAfter(new ApiKeyAuthenticationFilter(), LogoutFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/login", "/error").permitAll()
                        .requestMatchers("/playgrounds/view").permitAll()
                        .requestMatchers("/playgrounds/add").hasRole("USER")
                        .requestMatchers("/user").authenticated()
                        .anyRequest().denyAll()
                );

        return http.build();
    }

    @Bean
    @Order(1)
    SecurityFilterChain api(HttpSecurity http) throws Exception {
        http.securityMatcher("/api/**")
                .addFilterAfter(new ApiKeyAuthenticationFilter(), LogoutFilter.class)
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers("/api/playgrounds").authenticated()
                        .anyRequest().denyAll()
                );
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("martin")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
