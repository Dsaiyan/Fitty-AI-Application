CREATE TABLE users
(
    id          VARCHAR(255) NOT NULL,
    keycloak_id VARCHAR(255),
    email       VARCHAR(255) NOT NULL,
    first_name  VARCHAR(255),
    last_name   VARCHAR(255),
    created_at  TIMESTAMP WITHOUT TIME ZONE,
    updated_at  TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

ALTER TABLE users
    ADD CONSTRAINT uc_users_email UNIQUE (email);