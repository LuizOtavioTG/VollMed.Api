CREATE TABLE usuarios (
                         id BIGSERIAL PRIMARY KEY,
                         login VARCHAR(100) NOT NULL,
                         senha VARCHAR(255) NOT NULL
);