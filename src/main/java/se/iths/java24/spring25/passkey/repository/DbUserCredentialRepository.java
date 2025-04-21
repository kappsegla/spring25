package se.iths.java24.spring25.passkey.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.webauthn.api.*;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import se.iths.java24.spring25.passkey.domain.PasskeyCredential;
import se.iths.java24.spring25.user.domain.User;

import java.util.*;
import java.util.stream.Collectors;

public class DbUserCredentialRepository implements UserCredentialRepository {

    private static Logger log = LoggerFactory.getLogger(DbUserCredentialRepository.class);

    private final PasskeyCredentialRepository credentialRepository;
    private final PasskeyUserRepository userEntityRepository;

    public DbUserCredentialRepository(PasskeyCredentialRepository credentialRepository, PasskeyUserRepository userEntityRepository) {
        this.credentialRepository = credentialRepository;
        this.userEntityRepository = userEntityRepository;
    }

    @Override
    public void delete(Bytes credentialId) {
        log.info("delete: id={}", credentialId.toBase64UrlString());
        credentialRepository.findByCredentialId(credentialId.toBase64UrlString())
                .ifPresent(credentialRepository::delete);
    }

    @Override
    public void save(CredentialRecord credentialRecord) {

        userEntityRepository.findByExternalId(credentialRecord.getUserEntityUserId().toBase64UrlString())
                .ifPresent(user -> {
                    credentialRepository.findByCredentialId(credentialRecord.getCredentialId().toBase64UrlString())
                            .map((existingCredential) ->
                                    credentialRepository.save(toPasskeyCredential(existingCredential, credentialRecord, user))
                            )
                            .orElseGet(() ->
                                    credentialRepository.save(toPasskeyCredential(credentialRecord, user))
                            );

                    log.info("save: user={}, externalId={}, label={}", user.getUsername(), user.getExternalId(), credentialRecord.getLabel());
                });
    }

    @Override
    public CredentialRecord findByCredentialId(Bytes credentialId) {
        log.info("findByCredentialId: id={}", credentialId.toBase64UrlString());
        return credentialRepository.findByCredentialId(credentialId.toBase64UrlString())
                .map(cred -> {
                    var user = userEntityRepository.findById(Objects.requireNonNull(cred.getUser()
                            .getId())).orElseThrow();
                    return toCredentialRecord(cred, Bytes.fromBase64(user.getExternalId()));
                })
                .orElse(null);
    }

    @Override
    public List<CredentialRecord> findByUserId(Bytes userId) {
        log.info("findByUserId: userId={}", userId);

        Optional<User> user = userEntityRepository.findByExternalId(userId.toBase64UrlString());
        return user.map(passkeyUser -> credentialRepository.findByUser(passkeyUser.getId())
                        .stream()
                        .map(cred -> toCredentialRecord(cred, Bytes.fromBase64(passkeyUser.getExternalId())))
                        .collect(Collectors.toList()))
                .orElseGet(List::of);
    }

    private static CredentialRecord toCredentialRecord(PasskeyCredential credential, Bytes userId) {
        log.info("toCredentialRecord: credentialId={}, userId={}", credential.getCredentialId(), userId);
        return ImmutableCredentialRecord.builder()
                .userEntityUserId(userId)
                .label(credential.getLabel())
                .credentialType(PublicKeyCredentialType.valueOf(credential.getCredentialType()))
                .credentialId(Bytes.fromBase64(credential.getCredentialId()))
                .publicKey(ImmutablePublicKeyCose.fromBase64(credential.getPublicKeyCose()))
                .signatureCount(credential.getSignatureCount())
                .uvInitialized(credential.getUvInitialized())
                .transports(asTransportSet(credential.getTransports()))
                .backupEligible(credential.getBackupEligible())
                .backupState(credential.getBackupState())
                .attestationObject(Bytes.fromBase64(credential.getAttestationObject()))
                .lastUsed(credential.getLastUsed())
                .created(credential.getCreated())
                .build();
    }

    private static Set<AuthenticatorTransport> asTransportSet(String transports) {
        if (transports == null || transports.isEmpty()) {
            return Set.of();
        }
        return Set.of(transports.split(","))
                .stream()
                .map(AuthenticatorTransport::valueOf)
                .collect(Collectors.toSet());
    }

    private static PasskeyCredential toPasskeyCredential(PasskeyCredential credential, CredentialRecord credentialRecord, User user) {
        credential.setUser(user);
        credential.setLabel(credentialRecord.getLabel());
        credential.setCredentialType(credentialRecord.getCredentialType().getValue());
        credential.setCredentialId(credentialRecord.getCredentialId().toBase64UrlString());
        credential.setPublicKeyCose(Base64.getUrlEncoder().encodeToString(credentialRecord.getPublicKey().getBytes()));
        credential.setSignatureCount(credentialRecord.getSignatureCount());
        credential.setUvInitialized(credentialRecord.isUvInitialized());
        credential.setTransports(credentialRecord.getTransports().stream().map(AuthenticatorTransport::getValue).collect(Collectors.joining(",")));
        credential.setBackupEligible(credentialRecord.isBackupEligible());
        credential.setBackupState(credentialRecord.isBackupState());
        credential.setAttestationObject(credentialRecord.getAttestationObject().toBase64UrlString());
        credential.setLastUsed(credentialRecord.getLastUsed());
        credential.setCreated(credentialRecord.getCreated());

        return credential;
    }

    private static PasskeyCredential toPasskeyCredential(CredentialRecord credentialRecord, User user) {
        return toPasskeyCredential(new PasskeyCredential(), credentialRecord, user);
    }
}
