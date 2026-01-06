CREATE TABLE books
(
    id               UUID PRIMARY KEY      DEFAULT gen_random_uuid(),
    title            VARCHAR(255) NOT NULL,
    author           VARCHAR(255) NOT NULL,
    isbn             VARCHAR(36)  NOT NULL UNIQUE,
    publisher        VARCHAR(100) NOT NULL,
    publication_year INT,
    total_copies     INT          NOT NULL DEFAULT 0,
    available_copies INT          NOT NULL DEFAULT 0
);