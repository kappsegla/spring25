ALTER TABLE passkey_credentials
    ALTER COLUMN credential_id TYPE VARCHAR (4096) USING (credential_id:: VARCHAR (4096));
