ALTER TABLE users
    ADD google_sub VARCHAR(255);

ALTER TABLE users
    ADD CONSTRAINT uc_users_googlesub UNIQUE (google_sub);