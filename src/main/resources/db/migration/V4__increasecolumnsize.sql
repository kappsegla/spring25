ALTER TABLE passkey_credentials
    ALTER COLUMN attestation_object TYPE VARCHAR (4096) USING (attestation_object:: VARCHAR (4096));

ALTER TABLE passkey_credentials
    ALTER COLUMN public_key_cose TYPE VARCHAR (2048) USING (public_key_cose:: VARCHAR (2048));
