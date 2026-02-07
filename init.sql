CREATE TABLE users
(
    id       BIGSERIAL PRIMARY KEY,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE places
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    place_type  VARCHAR(255) NOT NULL,
    region      VARCHAR(255) NOT NULL,
    image_name  VARCHAR(255) NOT NULL,
    description TEXT,

    user_id     BIGINT,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL
);

CREATE TABLE reviews
(
    id       BIGSERIAL PRIMARY KEY,
    text     TEXT    NOT NULL,
    rating   INTEGER NOT NULL,

    user_id  BIGINT,
    place_id BIGINT,

    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE SET NULL,
    FOREIGN KEY (place_id) REFERENCES places (id) ON DELETE CASCADE
);