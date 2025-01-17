CREATE TABLE game (
    id CHAR(36) NOT NULL PRIMARY KEY,
    publisher_id VARCHAR(10) NOT NULL,
    name VARCHAR(50) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    deleted_at DATETIME(6) NULL
);

CREATE TABLE time_played (
    id INT AUTO_INCREMENT PRIMARY KEY,
    game_id CHAR(36) NOT NULL,
    date DATE NOT NULL,
    time INT NOT NULL, --> Time played in hours
    CONSTRAINT fk_game_id FOREIGN KEY (game_id) REFERENCES game (id) ON DELETE CASCADE,
    UNIQUE (game_id, date)
);