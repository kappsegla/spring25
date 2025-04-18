package se.iths.java24.spring25.passkey.repository;


import org.springframework.data.repository.CrudRepository;
import se.iths.java24.spring25.passkey.domain.PasskeyUser;

import java.util.Optional;

public interface PasskeyUserRepository extends CrudRepository<PasskeyUser, Long> {
    Optional<PasskeyUser> findByName(String name);
    Optional<PasskeyUser> findByExternalId(String externalId);
}
