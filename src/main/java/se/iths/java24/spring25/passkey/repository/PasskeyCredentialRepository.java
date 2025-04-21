package se.iths.java24.spring25.passkey.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import se.iths.java24.spring25.passkey.domain.PasskeyCredential;

import java.util.List;
import java.util.Optional;

public interface PasskeyCredentialRepository extends CrudRepository<PasskeyCredential, Long> {
    Optional<PasskeyCredential> findByCredentialId(String credentialId);

    @Query("select pc from PasskeyCredential pc where pc.user.id = :userId")
    List<PasskeyCredential> findByUser(@Param("userId") long userId);
}
