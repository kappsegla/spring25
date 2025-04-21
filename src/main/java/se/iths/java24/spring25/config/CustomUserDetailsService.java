package se.iths.java24.spring25.config;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import se.iths.java24.spring25.passkey.repository.PasskeyUserRepository;

import java.util.stream.Collectors;

public class CustomUserDetailsService implements UserDetailsService {

    private final PasskeyUserRepository userRepository;

    public CustomUserDetailsService(PasskeyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getUsername(),
                        user.getPassword(),
                        user.isEnabled(),
                        true, // accountNonExpired
                        true, // credentialsNonExpired
                        true, // accountNonLocked
                        user.getRoles().stream()
                                .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                                .collect(Collectors.toSet())
                ))
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
    }
}
