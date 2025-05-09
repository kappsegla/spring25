package se.iths.java24.spring25.passkey.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.webauthn.api.Bytes;
import org.springframework.security.web.webauthn.api.ImmutablePublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.api.PublicKeyCredentialUserEntity;
import org.springframework.security.web.webauthn.management.PublicKeyCredentialUserEntityRepository;
import se.iths.java24.spring25.user.domain.User;


public class DbPublicKeyCredentialUserEntityRepository implements PublicKeyCredentialUserEntityRepository {

    private static Logger log = LoggerFactory.getLogger(DbPublicKeyCredentialUserEntityRepository.class);

    private final PasskeyUserRepository userRepository;

    public DbPublicKeyCredentialUserEntityRepository(PasskeyUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public PublicKeyCredentialUserEntity findById(Bytes id) {
        log.info("findById: id={}", id.toBase64UrlString());
        var externalId = id.toBase64UrlString();
        return userRepository.findByExternalId(externalId)
                .map(DbPublicKeyCredentialUserEntityRepository::mapToUserEntity)
                .orElse(null);
    }

    @Override
    public PublicKeyCredentialUserEntity findByUsername(String username) {
        log.info("findByUsername: username={}", username);
        return userRepository.findByUsername(username)
                .map(DbPublicKeyCredentialUserEntityRepository::mapToUserEntity)
                .orElse(null);
    }

    @Override
    public void save(PublicKeyCredentialUserEntity userEntity) {
        log.info("save: username={}, externalId={}", userEntity.getName(),userEntity.getId().toBase64UrlString());
        var entity = userRepository.findByExternalId(userEntity.getId().toBase64UrlString())
                .orElse(new User());

        entity.setExternalId(userEntity.getId().toBase64UrlString());
        entity.setUsername(userEntity.getName());
        entity.setDisplayName(userEntity.getDisplayName());

        userRepository.save(entity);
    }

    @Override
    public void delete(Bytes id) {
        log.info("delete: id={}", id.toBase64UrlString());
        userRepository.findByExternalId(id.toBase64UrlString())
                .ifPresent(userRepository::delete);
    }

    private static PublicKeyCredentialUserEntity mapToUserEntity(User user) {
        return ImmutablePublicKeyCredentialUserEntity.builder()
                .id(Bytes.fromBase64(user.getExternalId()))
                .name(user.getUsername())
                .displayName(user.getDisplayName())
                .build();
    }
}
