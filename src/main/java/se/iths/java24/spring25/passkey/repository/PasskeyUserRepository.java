package se.iths.java24.spring25.passkey.repository;


import org.springframework.data.repository.CrudRepository;
import se.iths.java24.spring25.user.domain.User;

import java.util.Optional;

public interface PasskeyUserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByExternalId(String externalId);
}
