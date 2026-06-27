CREATE TABLE todos (
                       id UUID PRIMARY KEY,
                       text VARCHAR(255) NOT NULL,
                       completed BOOLEAN NOT NULL DEFAULT FALSE
);