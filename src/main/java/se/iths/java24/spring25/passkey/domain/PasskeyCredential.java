package se.iths.java24.spring25.passkey.domain;

import jakarta.persistence.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "PASSKEY_CREDENTIALS")
public class PasskeyCredential {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false)
    private PasskeyUser user;

    @Column(name = "LABEL")
    private String label;

    @Column(name = "CREDENTIAL_TYPE")
    private String credentialType;

    @Column(name = "CREDENTIAL_ID")
    private String credentialId;

    @Column(name = "PUBLIC_KEY_COSE")
    private String publicKeyCose;

    @Column(name = "SIGNATURE_COUNT")
    private Long signatureCount;

    @Column(name = "UV_INITIALIZED")
    private Boolean uvInitialized;

    @Column(name = "TRANSPORTS")
    private String transports;

    @Column(name = "BACKUP_ELIGIBLE")
    private Boolean backupEligible;

    @Column(name = "BACKUP_STATE")
    private Boolean backupState;

    @Column(name = "ATTESTATION_OBJECT")
    private String attestationObject;

    @Column(name = "LAST_USED")
    private Instant lastUsed;

    @Column(name = "CREATED")
    private Instant created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PasskeyUser getUser() {
        return user;
    }

    public void setUser(PasskeyUser user) {
        this.user = user;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }

    public String getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(String credentialId) {
        this.credentialId = credentialId;
    }

    public String getPublicKeyCose() {
        return publicKeyCose;
    }

    public void setPublicKeyCose(String publicKeyCose) {
        this.publicKeyCose = publicKeyCose;
    }

    public Long getSignatureCount() {
        return signatureCount;
    }

    public void setSignatureCount(Long signatureCount) {
        this.signatureCount = signatureCount;
    }

    public Boolean getUvInitialized() {
        return uvInitialized;
    }

    public void setUvInitialized(Boolean uvInitialized) {
        this.uvInitialized = uvInitialized;
    }

    public String getTransports() {
        return transports;
    }

    public void setTransports(String transports) {
        this.transports = transports;
    }

    public Boolean getBackupEligible() {
        return backupEligible;
    }

    public void setBackupEligible(Boolean backupEligible) {
        this.backupEligible = backupEligible;
    }

    public Boolean getBackupState() {
        return backupState;
    }

    public void setBackupState(Boolean backupState) {
        this.backupState = backupState;
    }

    public String getAttestationObject() {
        return attestationObject;
    }

    public void setAttestationObject(String attestationObject) {
        this.attestationObject = attestationObject;
    }

    public Instant getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Instant lastUsed) {
        this.lastUsed = lastUsed;
    }

    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        PasskeyCredential that = (PasskeyCredential) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
